/**
 * Kuebiko - NotePanelTest.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import dmh.kuebiko.controller.NoteManager;
import dmh.kuebiko.model.Note;
import dmh.kuebiko.test.TestHelper;
import dmh.swing.huxley.HuxleyUiManager;

/**
 * TestNG test class for the UI class {@link NotePanel}.
 *
 * @author davehuffman
 */
public class NotePanelTest {
    @Test
    public void multipleUpdateTest() {
        final String magicString = "!";

        final NoteManager noteMngr = new NoteManager(TestHelper.newDummyNoteDao());
        final Note note = noteMngr.getNoteAt(0);
        final String initialNoteText = note.getText();
        
        assertFalse(initialNoteText.contains(magicString), 
                "Initial note text should not contain magic string.");
        
        final NotePanel notePanel = new NotePanel();
        final HuxleyUiManager huxley = notePanel.getHuxleyUiManager();
        notePanel.setNote(note);

        for (int i = 0; i < 5; i++) {
            final String cumulativeMagicString = StringUtils.repeat(magicString, i + 1);
            
            assertFalse(huxley.getText().contains(cumulativeMagicString), 
                    "UI text should not yet contain cumulative magic string. " + i);
            
            huxley.setText(initialNoteText + cumulativeMagicString);
            
            assertTrue(huxley.getText().contains(cumulativeMagicString), 
                    "UI text should contain cumulative magic string. " + i);
            
            assertFalse(note.getText().contains(cumulativeMagicString), 
                    "Changed note text should not yet contain cumulative magic string. " + i);
            
            notePanel.syncNote();
            
            assertTrue(note.getText().contains(cumulativeMagicString), 
                    "Changed note text should contain cumulative magic string. " + i);
            
            noteMngr.saveAll();
            
            assertTrue(note.getText().contains(cumulativeMagicString), 
                    "Changed note text should still contain cumulative magic string. " + i);
            
            Note refreshedNote = noteMngr.getNoteAt(0);
            Assert.assertEquals(refreshedNote.getTitle(), note.getTitle(), 
                    "Note title should not change.");
            assertTrue(refreshedNote.getText().contains(cumulativeMagicString), 
                    "Refreshed note text should contain cumulative magic string. " + i);
        }
    }
}
