/**
 * Kuebiko - FileSystemNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import static dmh.kuebiko.model.DaoParameter.getParam;

import java.io.File;
import java.io.FileFilter;
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
import java.util.Set;

import com.google.common.collect.Lists;

/**
 * Note data access object (DAO) for storing notes in the file system.
 *
 * @author davehuffman
 */
public class FileSystemNoteDao extends AbstractNoteDao implements NoteTextLazyLoader {
    private static final String FILE_EXTENSION = "html";

    public static final Set<DaoParameter> REQUIRED_PARAMETERS = 
            Collections.unmodifiableSet(EnumSet.of(DaoParameter.DIRECTORY));

    private Map<String, String> params;
    private File noteDir;
    
    private List<Note> notes;
    
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

    @Override
    public List<Note> readNotes() throws IOException {
        File[] noteFiles = noteDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+?\\.html");
            }});
        
        notes = Lists.newArrayListWithCapacity(noteFiles.length);
        
        int id = 0;
        for (File noteFile: noteFiles) {
            String name = noteFile.getName().replace(".html", "");
            Date createDate = null; // XXX the file API doesn't have a way to get this because not all platforms support it. I'll have to embed the information in the file itself.
            notes.add(new Note(id++, name, createDate, new Date(noteFile.lastModified()), this)); 
        }
        return notes;
    }

    @Override
    protected Note findNote(int id) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected Note findNote(String title) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected Note persistActionAdd(Note addedNote) throws PersistenceException {
        File file = new File(noteDir, 
                String.format("%s.%s", addedNote.getTitle(), FILE_EXTENSION));

        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file));
            out.write(addedNote.getText());
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
        return addedNote; // XXX consider refreshing the note prior to returning it.
    }

    @Override
    protected void persistActionDelete(Note deletedNote) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    protected Note persistActionUpdate(Note updatedNote) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    @Override
    public String loadText(Note note) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public String getDirectory() {
        return getParam(params, DaoParameter.DIRECTORY);
    }
}
