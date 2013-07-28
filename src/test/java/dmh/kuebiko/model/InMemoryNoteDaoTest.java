/**
 * Kuebiko - InMemoryNoteDaoTest.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
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
