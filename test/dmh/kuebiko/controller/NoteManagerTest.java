/**
 * jCap - NoteManagerTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.controller;

import org.testng.annotations.Test;


import dmh.kuebiko.model.Note;
import dmh.kuebiko.test.TestHelper;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public class NoteManagerTest {
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void immutableNotesTest() {
        TestHelper.newNoteManager().getNotes().add(new Note());
    }
}
