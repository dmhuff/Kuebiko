/**
 * Kuebiko - FileSystemNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import static dmh.kuebiko.model.DaoParameter.getParam;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Note data access object (DAO) for storing notes in the file system.
 *
 * @author davehuffman
 */
public class FileSystemNoteDao extends AbstractNoteDao implements NoteTextLazyLoader {
    static final String FILE_EXTENSION = "html";
    
    private static final String NEW_LINE = System.getProperty("line.separator");

    public static final Set<DaoParameter> REQUIRED_PARAMETERS = 
            Collections.unmodifiableSet(EnumSet.of(DaoParameter.DIRECTORY));

    private Map<String, String> params;
    private File noteDir;
    
    // XXX consider moving the note cache logic to a generic entity cache that will encapsulate the list.
    private List<Note> noteCache = null;
    
    private Map<Integer, File> fileMap = null;
    
    /**
     * Helper method; convert a file's name to the title of the note it represents.
     * @param fileName A file name string. May not be null.
     * @return The converted note title.
     */
    private String fileNameToNoteTitle(String fileName) {
        return fileName.replace("." + FILE_EXTENSION, "");
    }
    
    /**
     * Helper method; convert a note's title to the name of the file where it's
     * data is stored.
     * @param noteTitle A note title string; May not be null.
     * @return The converted file name.
     */
    private String noteTitleToFileName(String noteTitle) {
        return noteTitle + "." + FILE_EXTENSION;
    }
    
    /**
     * Helper method; get the file object for a note.
     * @param note The note in question.
     * @return The passed's note file.
     */
    private File getNoteFile(Note note) {
        return new File(noteDir, noteTitleToFileName(note.getTitle()));
    }
    
    @Override
    public Set<DaoParameter> getRequiredParameters() {
        return REQUIRED_PARAMETERS;
    }
    
    @Override
    public void initialize(Map<String, String> params) throws DaoConfigurationException {
        checkParameters(params);
        this.params = params;

        noteDir = new File(getDirectory());
        if (!noteDir.exists()) { 
            throw new DaoConfigurationException(
                    String.format("Note directory [%s] does not exist.", noteDir));
        }
    }
    
    private List<Note> loadNotes() {
        File[] noteFiles = noteDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+?\\." + FILE_EXTENSION);
            }});
        
        List<Note> notes = Lists.newArrayListWithCapacity(noteFiles.length);
        fileMap = Maps.newHashMap();
        int id = 1;
        for (File noteFile: noteFiles) {
            fileMap.put(id, noteFile);
            
            String name = fileNameToNoteTitle(noteFile.getName());
            Date createDate = null; // XXX the file API doesn't have a way to get this because not all platforms support it. I'll have to embed the information in the file itself.
            notes.add(new Note(id++, name, createDate, new Date(noteFile.lastModified()), this)); 
        }
        return notes;
    }
    
    private List<Note> getNoteCache() {
        if (noteCache == null) {
            noteCache = loadNotes();
        }
        return noteCache;
    }

    @Override
    public List<Note> readNotes() throws PersistenceException {
        try {
            return getNoteCache();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected Note findNote(int id) {
        if (noteCache == null) {
            throw new IllegalStateException("Note cache has been reset, ID may be invalid.");
        }
        
        for (Note note: getNoteCache()) {
            if (id == note.getId()) {
                return note;
            }
        }
        return null;
    }
    
    @Override
    protected Note findNote(String title) {
        // XXX consider using a directory listing for this.
        for (Note note: getNoteCache()) {
            if (title.equals(note.getTitle())) {
                return note;
            }
        }
        return null;
    }
    
    /**
     * @param note
     * @throws PersistenceException
     */
    private void writeNoteToFile(Note note) throws PersistenceException {
        File file = getNoteFile(note);

        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file));
            out.write(note.getText());
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
    }

    @Override
    protected Note persistActionAdd(Note addedNote) throws PersistenceException {
        writeNoteToFile(addedNote);
        // Reset the note cache.
        noteCache = null;
        
        return addedNote; // XXX consider refreshing the note prior to returning it.
    }

    @Override
    protected void persistActionDelete(Note deletedNote) throws PersistenceException {
        boolean success = getNoteFile(deletedNote).delete();
        if (success) {
            // Reset the note cache.
            noteCache = null;
        } else {
            throw new PersistenceException(
                    String.format("Unable to delete note [%s].", deletedNote.getTitle()));
        }
    }

    @Override
    protected Note persistActionUpdate(Note updatedNote) throws PersistenceException {
        // Delete the existing note file. XXX use file map
        if (!getNoteFile(updatedNote).delete()) //{
            throw new PersistenceException(
                    String.format("Unable to delete note [%s].", updatedNote.getTitle()));
        }
        
        // Write a new note file.
        writeNoteToFile(updatedNote);
        return updatedNote; // XXX consider refreshing the note prior to returning it.
    }
    
    @Override
    public String loadText(Note note) throws PersistenceException {
        final StringBuilder text = new StringBuilder();
        
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(getNoteFile(note)));
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine() + NEW_LINE);
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

    String getDirectory() {
        return getParam(params, DaoParameter.DIRECTORY);
    }
}
