/**
 * Kuebiko - FileSystemNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import dmh.kuebiko.model.AbstractNoteDao;
import dmh.kuebiko.model.DaoConfigurationException;
import dmh.kuebiko.model.DaoParameter;
import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.NoteTextLazyLoader;
import dmh.kuebiko.model.PersistenceException;
import dmh.kuebiko.util.LapCounter;

/**
 * Note data access object (DAO) for storing notes in the file system.
 *
 * @author davehuffman
 */
public class FileSystemNoteDao extends AbstractNoteDao implements NoteTextLazyLoader {
    public static final Set<DaoParameter> REQUIRED_PARAMETERS = 
            Collections.unmodifiableSet(EnumSet.of(DaoParameter.DIRECTORY));

    private LapCounter idGenerator = new LapCounter();
    private FileSystemNoteCache noteCache = null;
    private File noteDir;
    
    public FileSystemNoteDao() {
        super(REQUIRED_PARAMETERS);
    }
    
    @Override
    public void postInitialize() throws DaoConfigurationException {
        noteDir = new File(getDirectory());
        if (!noteDir.exists()) { 
            throw new DaoConfigurationException(
                    String.format("Note directory [%s] does not exist.", noteDir));
        }
    }
    
    private void loadNotes() {
        File[] noteFiles = NoteFileUtil.listNoteFilesInDir(noteDir);
        
        // Reset the internal data structures.
        noteCache = new FileSystemNoteCache(noteFiles.length);
        idGenerator = new LapCounter();

        for (File noteFile: noteFiles) {
            String name = NoteFileUtil.fileNameToNoteTitle(noteFile.getName());
            Date createDate = null; // TODO the file API doesn't have a way to get this because not all platforms support it. I'll have to embed the information in the file itself.
            Note note = newNote(name, createDate, 
                    new Date(noteFile.lastModified()), this); 
            
            noteCache.put(note.getId(), noteFile, note);
        }
    }

    /**
     * Retrieve all of the notes in the cache, reloading the cache if it has
     * been reset.
     * @return All known notes.
     */
    private Collection<Note> getNotesFromCache() {
        if (noteCache == null) {
            loadNotes();
        }
        return noteCache.getNotes();
    }

    @Override
    public List<Note> readNotes() throws PersistenceException {
        try {
            return Lists.newArrayList(getNotesFromCache());
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected Note findNote(int id) {
        if (noteCache == null) {
            throw new IllegalStateException("Note cache has been reset, ID may be invalid.");
        }
        
        for (Note note: getNotesFromCache()) {
            if (id == note.getId()) {
                return note;
            }
        }
        return null;
    }
    
    @Override
    protected Note findNote(String title) {
        for (Note note: getNotesFromCache()) {
            if (title.equals(note.getTitle())) {
                return note;
            }
        }
        return null;
    }
    
    @Override
    protected int getUniqueId() {
        return idGenerator.tick();
    }
    
    /**
     * Write (persist) note data to the file system.
     * @param note The note to persist.
     * @return The note's file.
     */
    private File writeNoteToFile(Note note) throws PersistenceException {
        Preconditions.checkNotNull(note);
        
        File noteFile = NoteFileUtil.getNoteFile(noteDir, note);

        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(noteFile));
            String noteText = note.getText();
            out.write(noteText == null? "" : noteText);
        } catch (FileNotFoundException fnfe) {
            throw new PersistenceException(fnfe);
        } catch (IOException ioe) {
            throw new PersistenceException(ioe);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException cioe) {
                    throw new PersistenceException(cioe);
                }
            }
        }
        return noteFile;
    }
    
    @Override
    protected Note persistActionAdd(Note addedNote) throws PersistenceException {
        File noteFile = writeNoteToFile(addedNote);
        noteCache.put(addedNote.getId(), noteFile, addedNote);
        
        return addedNote;
    }

    @Override
    protected void persistActionDelete(Note deletedNote) throws PersistenceException {
        // Find the note in the cache.
        final int noteId = deletedNote.getId();
        File noteFile = noteCache.getFile(noteId);
        if (noteFile == null) {
            throw new PersistenceException(
                    String.format("No note exists for ID [%d].", noteId));
        }
        
        // Delete the note file from the file system.
        boolean success = noteFile.delete();
        if (!success) {
            throw new PersistenceException(
                    String.format("Unable to delete note [%d:%s].", 
                            noteId, deletedNote.getTitle()));
        }
        
        // Update the cache.
        noteCache.remove(noteId);
    }

    @Override
    protected Note persistActionUpdate(Note updatedNote) throws PersistenceException {
        // Perform the update by replacing the old data.
        persistActionDelete(updatedNote);
        persistActionAdd(updatedNote);
        
        return updatedNote;
    }

    @Override
    public String loadText(Note note) throws PersistenceException {
        final StringBuilder text = new StringBuilder();
        
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(NoteFileUtil.getNoteFile(noteDir, note)));
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine() + NoteFileUtil.NEW_LINE);
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return text.toString();
    }

    public String getDirectory() {
        return getParameter(DaoParameter.DIRECTORY);
    }
}
