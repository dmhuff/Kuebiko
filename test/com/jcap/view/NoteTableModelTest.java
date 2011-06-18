/**
 * Kuebiko - NoteTableModelTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package com.jcap.view;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Collections;

import org.testng.annotations.Test;

import com.jcap.model.Note;

/**
 * Unit test class for NoteTableModel.
 * 
 * @see com.jcap.view.NoteTableModel
 * 
 * @author davehuffman
 */
public class NoteTableModelTest {
    @Test
    public void emptyModelTest() {
        NoteTableModel model = new NoteTableModel();

        assertNull(model.getValueAt(0, 0), "Model should be empty.");
        assertNotNull(model.getNotes(), "Model notes shoud never be null.");
    }

    @Test
    public void getValueAtTest() {
        Note note = new Note();
        note.setTitle("foobar");
        NoteTableModel model = new NoteTableModel(
                Collections.singletonList(note));
        final Object coordVal = model.getValueAt(0, 0);
        final Object enumVal = model.getValueAt(0, NoteTableModel.Column.TITLE);

        assertNotNull(coordVal, "Model should have a value.");
        assertEquals(coordVal, note.getTitle());

        assertNotNull(enumVal, "Model should have a value.");
        assertEquals(enumVal, note.getTitle());

        assertTrue(coordVal.equals(enumVal),
                "Both getValueAt(...) methods should return the same result.");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void immutableNotesTest() {
        new NoteTableModel().getNotes().add(new Note());
    }
}
