/**
 * jCap - NoteUpdateTestHarness.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.TestException;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.Note.State;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
abstract class NoteUpdateTestHarness {
    private final Note testNote;
    private final boolean origIsNew;
    private final int origId;
    private final Date origCreateDate;
    private final Date origModifiedDate;

    NoteUpdateTestHarness(Note testNote) {
        this.testNote = testNote;
        origIsNew = testNote.isNew();
        origId = testNote.getId();
        origCreateDate = testNote.getCreateDate();
        origModifiedDate = testNote.getModifiedDate();
    }
    
    final Note runTest() throws Exception {
        beforeUpdate();
        
        // Sleep to make sure the modified date changes during the update.
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            throw new TestException(e);
        }
        
        Note updatedNote = performUpdate(testNote);
        afterUpdate(updatedNote);
        return updatedNote;
    }

    void beforeUpdate() {
        if (testNote.isNew()) {
            assertTrue(testNote.getModifiedDate() == null, 
                    "Note should not have a modified date.");
        } else {
            assertTrue(testNote.isDirty(), "Note to be updated should be dirty.");
        }
    }

    /**
     * Perform validations on a note after it has been updated.
     * @param updatedNote The updated note to validate.
     */
    void afterUpdate(Note updatedNote) {
        // Validate the note's state.
        assertNotNull(updatedNote, "Note cannot be null.");
        assertFalse(updatedNote.isNew(), "Note should not be new.");
        
        State expectedState = updatedNote.isLazy()? State.HOLLOW : State.CLEAN;
        assertEquals(updatedNote.getState(), expectedState, 
                String.format("Note should be [%s].", expectedState));
        
        // Validate the note's audit dates (note: create date is not universally
        // supported, in which case the create date value will be null).
        assertEquals(updatedNote.getCreateDate(), origCreateDate, 
                "Create date should not change.");
        assertNotNull(updatedNote.getModifiedDate(), "Modified date should be set.");
        
        if (updatedNote.getCreateDate() != null) {
            assertTrue(updatedNote.getModifiedDate().after(updatedNote.getCreateDate()), 
                    "Modified date should be after create date.");
        }
        
        if (origIsNew) {
            assertFalse(updatedNote.getId() == origId, 
                    "Note should have different ID assigned.");
        } else {
            assertEquals(updatedNote.getId(), origId, "Note should have same ID.");
            assertTrue(updatedNote.getModifiedDate().after(origModifiedDate), 
                    "Modified date should be after original.");
        }
    }

    abstract Note performUpdate(Note testNote) throws Exception;
}
