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

    void afterUpdate(Note updatedNote) {
        assertNotNull(updatedNote, "Note cannot be null.");
        assertEquals(updatedNote.getState(), State.CLEAN, "Note should be clean.");
        assertFalse(updatedNote.isNew(), "Note should not be new.");
        assertEquals(updatedNote.getCreateDate(), origCreateDate, 
                "Create date should not change.");
        if (origIsNew) {
            assertFalse(updatedNote.getId() == origId, 
                    "Note should have different ID assigned.");
            assertNotNull(updatedNote.getModifiedDate(), 
                    "Modified date should be set.");
        } else {
            assertEquals(updatedNote.getId(), origId, "Note should have same ID.");
            assertTrue(updatedNote.getModifiedDate().after(origModifiedDate), 
                    "Modified date should be after original.");
        }
        assertTrue(updatedNote.getModifiedDate().after(updatedNote.getCreateDate()), 
                "Modified date should be after create date.");
    }

    abstract Note performUpdate(Note testNote) throws Exception;
}