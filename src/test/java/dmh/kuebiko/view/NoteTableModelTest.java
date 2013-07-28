/**
 * Kuebiko - NoteTableModelTest.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */

package dmh.kuebiko.view;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.test.TestHelper;

/**
 * Unit test class for NoteTableModel.
 * @see dmh.kuebiko.view.NoteTableModel
 *
 * @author davehuffman
 */
public class NoteTableModelTest {
    @Test
    public void emptyModelTest() {
        NoteTableModel model = TestHelper.newNoteTableModel();

        assertNull(model.getValueAt(0, 0), "Model should be empty.");
        assertTrue(model.getRowCount() == 0, "Model should not contain any rows.");
    }

    @Test
    public void getValueAtTest() {
        Note note = new Note();
        note.setTitle("foobar");
        NoteTableModel model = TestHelper.newNoteTableModel(note);
        final Object coordVal = model.getValueAt(0, 0);
        final Object enumVal = model.getValueAt(0, NoteTableModel.Column.TITLE);

        assertNotNull(coordVal, "Model should have a value.");
        assertEquals(coordVal, note.getTitle());

        assertNotNull(enumVal, "Model should have a value.");
        assertEquals(enumVal, note.getTitle());

        assertTrue(coordVal.equals(enumVal),
                "Both getValueAt(...) methods should return the same result.");
    }
}
