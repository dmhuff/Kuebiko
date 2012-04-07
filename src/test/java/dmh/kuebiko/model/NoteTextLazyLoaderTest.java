/**
 * Kuebiko - NoteLazyLoaderTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

/**
 * TestNG test class for the {@link NoteTextLazyLoader} interface.
 *
 * @author davehuffman
 */
public class NoteTextLazyLoaderTest implements NoteTextLazyLoader {
    private static final String DUMMY_TEXT = "text";

    private boolean loadTextCalled = false;
    
    @Test
    public void loadTextTest() {
        Note note = new Note(0, "title", new Date(), new Date(), this);
        assertTrue(note.isHollow(), "Note should be hollow.");
        assertFalse(loadTextCalled, "loadText() should not yet be called.");
        
        final String noteText = note.getText();
        assertEquals(noteText, DUMMY_TEXT, "Note should have loaded text.");
        assertFalse(note.isHollow(), "Note should no longer be hollow.");
        assertTrue(note.isClean(), "Note should be clean.");
        assertTrue(loadTextCalled, "loadText() should be called.");
    }

    @Override
    public String loadText(Note note) {
        loadTextCalled = true;
        return DUMMY_TEXT;
    }
}
