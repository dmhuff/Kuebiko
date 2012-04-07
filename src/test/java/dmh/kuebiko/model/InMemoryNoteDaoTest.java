/**
 * Kuebiko - InMemoryNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import org.testng.Assert;
import org.testng.TestException;

/**
 * TestNG test class for {@link InMemoryNoteDao}.
 *
 * @author davehuffman
 */
public class InMemoryNoteDaoTest extends AbstractNoteDaoTest {
    @Override
    protected AbstractNoteDao newNoteDao() {
        try {
            NoteDao inMemNoteDao = NoteDaoFactory.get(InMemoryNoteDao.class.getName());
            Assert.assertTrue(inMemNoteDao instanceof InMemoryNoteDao, 
                    "Factory should return DAO of expected type.");
            return (AbstractNoteDao) inMemNoteDao;
        } catch (Exception e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
}
