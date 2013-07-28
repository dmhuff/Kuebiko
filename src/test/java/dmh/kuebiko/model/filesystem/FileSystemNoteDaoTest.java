/**
 * Kuebiko - FileSystemNoteDaoTest.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */

package dmh.kuebiko.model.filesystem;

import static dmh.kuebiko.test.TestHelper.newDummyNote;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

import dmh.kuebiko.model.AbstractNoteDao;
import dmh.kuebiko.model.AbstractNoteDaoTest;
import dmh.kuebiko.model.DaoConfigurationException;
import dmh.kuebiko.model.DaoParameter;
import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.NoteDaoFactory;
import dmh.kuebiko.util.BadClassException;
import dmh.kuebiko.util.Pair;

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
        // are complete, and this class subclasses AbstractNoteDaoTest.
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
    protected AbstractNoteDao newNoteDao() {
        try {
            NoteDao noteDao = NoteDaoFactory.get(FileSystemNoteDao.class.getName(), params);
            Assert.assertTrue(noteDao instanceof FileSystemNoteDao,
                    "Factory should return DAO of expected type.");
            return (AbstractNoteDao) noteDao;
        } catch (Exception e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }

    private FileSystemNoteDao newFileSystemNoteDao() {
        return (FileSystemNoteDao) newNoteDao();
    }

    @Test
    public void parameterTest() {
        FileSystemNoteDao fsDao = newFileSystemNoteDao();

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

    /**
     * Test the life cycle of a single note, from creation to deletion.
     */
    @Test
    public void lifeCycleTest() throws Exception {
        final String dummyTitle = "The Great Gatsby";
        final String dummyText = "by F. Scott Fitzgerald";
        final NoteDao dao = newNoteDao();

        // Create a single note.
        dao.addNote(newDummyNote(dummyTitle , dummyText));

        // Check the contents of the storage directory for the new note.
        final File[] dataFiles = tempDir.listFiles();
        assertEquals(dataFiles.length, 1, "One note should exist.");
        assertEquals(dataFiles[0].getName(),
                String.format("%s.%s", dummyTitle, NoteFileUtil.FILE_EXTENSION),
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

    @Test
    public void multipleDaoCrudTest() throws Exception {
        final List<Pair<String, String>> redShirts = Pair.list(
                "Captain Picard",  "Enterprise",
                "Commander Sisko", "Deep Space Nine",
                "Captain Janeway", "Voyager");

        // Write an initial note.
        {
            final NoteDao writeOneDao = newNoteDao();
            writeOneDao.addNote(newDummyNote("foo", "bar"));
            checkIntegrity(writeOneDao, 1);
        }

        // Read the first note and save it for later.
        FileSystemNoteDao tempFsDao = newFileSystemNoteDao();
        //tempFsDao.readNotes();
        final Note firstNote = tempFsDao.findNote("foo");
        assertNotNull(firstNote, "First note should exist");

        // Write a bunch of new notes.
        {
            final NoteDao writeManyDao = newNoteDao();
            for (Pair<String, String> datum: redShirts) {
                writeManyDao.addNote(newDummyNote(datum.first, datum.second));
            }
            checkIntegrity(writeManyDao, redShirts.size() + 1);
        }

        // Check what's been done so far with a new DAO.
        checkIntegrity(newNoteDao(), redShirts.size() + 1);

        // Update a note.
        {
            NoteDao updateDao = newNoteDao();
            Note updateNote = updateDao.readNotes().get(0);

            // Be sure to read the text before setting it because note text
            // is lazy loaded.
            updateNote.setText(updateNote.getText() + " chunky bacon!");

            updateDao.updateNote(updateNote);
        }

        // Check again after the update.
        checkIntegrity(newNoteDao(), redShirts.size() + 1);

        // Delete a note.
        {
            NoteDao deleteDao = newNoteDao();
            Note deleteNote = deleteDao.readNotes().get(0);
            deleteDao.deleteNote(deleteNote);
        }

        // Do one last check.
        checkIntegrity(newNoteDao(), redShirts.size());
    }
}
