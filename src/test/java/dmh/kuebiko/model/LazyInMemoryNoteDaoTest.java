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
public class LazyInMemoryNoteDaoTest extends AbstractNoteDaoTest {
    @Override
    protected AbstractNoteDao newNoteDao() {
        try {
            NoteDao dao = NoteDaoFactory.get(LazyInMemoryNoteDao.class.getName());
            Assert.assertTrue(dao instanceof LazyInMemoryNoteDao, 
                    "Factory should return DAO of expected type.");
            return (AbstractNoteDao) dao;
        } catch (Exception e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
}
