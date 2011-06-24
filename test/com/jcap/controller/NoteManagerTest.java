/**
 * jCap - NoteManagerTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.controller;

import org.testng.annotations.Test;

import com.jcap.model.Note;
import com.jcap.test.TestHelper;

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
