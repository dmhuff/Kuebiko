/**
 * jCap - NoteDaoMemoryTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;

import org.testng.TestException;

import com.jcap.util.BadClassException;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public class NoteDaoMemoryTest extends AbstractNoteDaoTest {
    @Override
    NoteDao newNoteDao() {
        try {
            return NoteDaoFactory.get("NoteDaoMemory");
        } catch (BadClassException e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
    }
}
