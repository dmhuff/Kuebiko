/**
 * Kuebiko - FileSystemNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import org.testng.Assert;
import org.testng.TestException;

import dmh.kuebiko.util.BadClassException;

/**
 * TestNG test class for {@link FileSystemNoteDao}.
 *
 * @author davehuffman
 */
public class FileSystemNoteDaoTest extends AbstractNoteDaoTest {
    @Override
    NoteDao newNoteDao() {
        try {
            NoteDao noteDao = NoteDaoFactory.get(FileSystemNoteDao.class.getName());
            Assert.assertTrue(noteDao instanceof FileSystemNoteDao, 
                    "Factory should return DAO of expected type.");
            return noteDao;
        } catch (BadClassException e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
}
