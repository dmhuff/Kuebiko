/**
 * Kuebiko - FileSystemNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import static dmh.kuebiko.test.TestHelper.newDummyNote;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import dmh.kuebiko.test.TestHelper;
import dmh.kuebiko.util.BadClassException;

/**
 * TestNG test class for {@link FileSystemNoteDao}.
 *
 * @author davehuffman
 */
public class FileSystemNoteDaoTest extends AbstractNoteDaoTest {
    private File tempDir;
    private Map<String, String> params;
    
    @BeforeMethod
    public void checkConfig() {
        // Ensure a premature cleanup hasn't occurred. This is necessary because 
        // TestNG will call the @AfterClass method when a superclass's methods 
        // are complete, and this class subclasses {@link AbstractNoteDaoTest}.
        if (tempDir == null) {
            tempDir = Files.createTempDir();
            params = Maps.newHashMap();
            params.put(DaoParameter.DIRECTORY.toString(), tempDir.getPath());
        }
    }
    
    @AfterMethod
    public void methodCleanup() throws IOException {
        // Clean the temp directory for the next test.
        FileUtils.cleanDirectory(tempDir);
    }
    
    @AfterClass
    public void classCleanup() throws IOException {
        FileUtils.deleteDirectory(tempDir);
        
        tempDir = null;
        params = null;
    }

    @Override
    NoteDao newNoteDao() {
        try {
            NoteDao noteDao = NoteDaoFactory.get(FileSystemNoteDao.class.getName(), params);
            Assert.assertTrue(noteDao instanceof FileSystemNoteDao, 
                    "Factory should return DAO of expected type.");
            return noteDao;
        } catch (Exception e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
    
    @Test
    public void parameterTest() {
        FileSystemNoteDao fsDao = (FileSystemNoteDao) newNoteDao();
        
        assertEquals(fsDao.getDirectory(), 
                params.get(DaoParameter.DIRECTORY.toString()));
    }
    
    @Test(expectedExceptions = DaoConfigurationException.class)
    public void nullParameterTest() 
    throws BadClassException, DaoConfigurationException {
        NoteDaoFactory.get(FileSystemNoteDao.class.getName(), null);
    }
    
    @Test(expectedExceptions = DaoConfigurationException.class)
    public void missingParameterTest() 
    throws BadClassException, DaoConfigurationException {
        HashMap<String, String> emptyParamMap = Maps.newHashMap();
        NoteDaoFactory.get(FileSystemNoteDao.class.getName(), emptyParamMap);
    }
    
    @Test
    public void lifecycleTest() throws Exception {
        final String dummyTitle = "The Great Gatsby";
        final String dummyText = "by F. Scott Fitzgerald";
        final NoteDao dao = NoteDaoFactory.get(FileSystemNoteDao.class.getName(), params);
        
        // Create a single note.
        dao.addNote(newDummyNote(dummyTitle , dummyText ));
        
        // Check the contents of the storage directory for the new note.
        final File[] dataFiles = tempDir.listFiles();
        assertEquals(dataFiles.length, 1, "One note should exist.");
        assertEquals(dataFiles[0].getName(), 
                String.format("%s.%s", dummyTitle, FileSystemNoteDao.FILE_EXTENSION), 
                "Note's name should use expected title.");
        
        // Test reading and lazy loading.
        final Note note = Iterables.getOnlyElement(dao.readNotes());
        assertEquals(note.getTitle(), dummyTitle, "Note should have expected title.");
        assertTrue(note.getText().contains(dummyText), "Note should have expected text.");
        
        // Delete the note.
        dao.deleteNote(note);
        
        // Check the contents of the storage directory for emptiness.
        assertEquals(tempDir.listFiles().length, 0, "No notes should exist.");
    }
}
