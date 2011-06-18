package com.jcap.model;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.testng.annotations.Test;

public class NoteTest {
    @Test
    public void dirtyTest() {
        Note note = new Note(42, "Note", "foobar", new Date(), new Date());
        assertFalse(note.isDirty(), "Note should not be dirty when created.");
        
        note.setTitle("something different");
        assertTrue(note.isDirty(), "Note should be dirty after update.");
        
        note.reset();
        assertFalse(note.isDirty(), "Note should not be dirty after reset.");
    }
}
