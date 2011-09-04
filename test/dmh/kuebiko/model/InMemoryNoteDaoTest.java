/**
 * jCap - NoteDaoMemoryTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import org.testng.TestException;


import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.NoteDaoFactory;
import dmh.kuebiko.util.BadClassException;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public class InMemoryNoteDaoTest extends AbstractNoteDaoTest {
    @Override
    NoteDao newNoteDao() {
        try {
            return NoteDaoFactory.get("NoteDaoMemory");
        } catch (BadClassException e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
}
