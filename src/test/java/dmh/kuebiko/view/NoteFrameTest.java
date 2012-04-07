/**
 * Kuebiko - NoteFrameText.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.Test;

import dmh.kuebiko.controller.NoteManager;
import dmh.kuebiko.test.TestHelper;

/**
 * TestNG test class for the UI class {@link NoteFrame}.
 *
 * @author davehuffman
 */
public class NoteFrameTest {
    @Test
    public void partialMatchSelectTabTest() {
        NoteManager noteMngr = new NoteManager(TestHelper.newDummyNoteDao());
        NoteFrame noteFrame = new NoteFrame(noteMngr);
        
        // Set focus on the search text field.
        noteFrame.searchText.requestFocus();
        // The first three characters of "Darth Vader".
        noteFrame.searchText.setText("Dar");
        // Simulate the user pressing the tab key, which will cause the search 
        // text field to lose focus.
        try {
            Method process = Component.class.getDeclaredMethod(
                    "processEvent", AWTEvent.class);
            process.setAccessible(true);
            process.invoke(noteFrame.searchText, 
                    new FocusEvent(noteFrame.searchText, FocusEvent.FOCUS_LOST));
        } catch (Exception e) {
            throw new TestException(e);
        }
        
//        String noteText = trimToNull(noteFrame.notePanel.getNoteTextArea().getText());
        String noteText = trimToNull(noteFrame.notePanel.getHuxleyUiManager().getText());
        Assert.assertNotNull(noteText, "The note text area should have text.");
    }
}
