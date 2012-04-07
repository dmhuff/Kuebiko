package dmh.kuebiko.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.Note.State;

public class NoteTest {
    private Note newDummyNote() {
        Note note = new Note(42, "Note", "foobar", new Date(), new Date());
        return note;
    }
    
    @Test
    public void newNoteTest() {
        Note note = new Note();
        assertTrue(note.isNew(), "Note should be new.");
        assertNotNull(note.getCreateDate(), "Note should have a create date.");
        assertTrue(note.getModifiedDate() == null, 
                "Note should not have a modified date.");
    }
    
    @Test
    public void dirtyTest() {
        Note note = newDummyNote();
        assertFalse(note.isDirty(), "Note should not be dirty when created.");
        
        note.setTitle("something different");
        assertTrue(note.isDirty(), "Note should be dirty after update.");
        
        note.reset();
        assertFalse(note.isDirty(), "Note should not be dirty after reset.");
    }

    @Test 
    void copyTest() {
        final Note origNote = newDummyNote();
        final int copyNoteId = origNote.getId() + 1;
        final Note copyNote = new Note(copyNoteId, origNote);
        
        // Ensure the copy is in the correct state.
        assertNotSame(copyNote, origNote, "Copy should not be the same object.");
        assertEquals(copyNote.getId(), copyNoteId, "Copy should have assigned ID.");
        assertFalse(copyNote.getId() == origNote.getId(), "Copy should have a different ID.");
        assertEquals(copyNote.getState(), State.CLEAN, "Copy should be clean.");
        
        // Ensure fields are the same.
        assertEquals(copyNote.getTitle(), copyNote.getTitle(), 
                "Copy title should be same as original.");
        assertEquals(copyNote.getText(), copyNote.getText(), 
                "Copy text should be same as original.");
        assertEquals(copyNote.getCreateDate(), copyNote.getCreateDate(), 
                "Copy create date should be same as original.");
        assertEquals(copyNote.getModifiedDate(), copyNote.getModifiedDate(), 
                "Copy modified date should be same as original.");
    }
}
