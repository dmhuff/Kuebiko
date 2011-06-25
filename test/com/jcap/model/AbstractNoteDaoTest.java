/**
 * Kuebiko - AbstractNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.jcap.model.Note.State;

/**
 * Base class for testing classes that implement the NoteDao interface. Tests 
 * basic functionality that all note DAOs should have.
 * @see com.jcap.model.NoteDao
 *
 * @author davehuffman
 */
public abstract class AbstractNoteDaoTest {
    private static final String DUMMY_NOTE_TITLE = "foobar";

    private final Note newDummyNote(String title) {
        Note dummyNote = new Note();
        dummyNote.setTitle(title);
        return dummyNote;
    }
    
    /**
     * @return A new instance of a note DAO.
     */
    abstract NoteDao newNoteDao();

    /**
     * Test the note DAO's behavior when adding a new note.
     */
    @Test(dependsOnMethods = "readNotesEmptyTest")
    public void addNoteTest() {
        NoteDao noteDao = newNoteDao();
        noteDao.addNote(newDummyNote(DUMMY_NOTE_TITLE));
        
        final List<Note> allNotes = noteDao.readNotes();
        assertNotNull(allNotes, "Result of readNotes() should never be null.");
        assertEquals(allNotes.size(), 1, "There should only be one note.");
        
        final Note onlyNote = Iterables.getOnlyElement(allNotes);
        assertEquals(onlyNote.getTitle(), DUMMY_NOTE_TITLE,
                "Only note should be the one added.");
        assertEquals(onlyNote.getState(), State.CLEAN, "Read note should be clean.");
    }
    
    /**
     * Test the note DAO's behavior when deleting an existing note.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesTest"})
    public void deleteNoteTest() {
        // TODO implement.
    }
    
    /**
     * Test the note DAO's behavior when updating an existing note.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesTest"})
    public void updateNoteTest() {
        // TODO implement.
    }
    
    /**
     * Test the note DAO's behavior when no notes exist.
     */
    @Test
    public void readNotesEmptyTest() {
        NoteDao noteDao = newNoteDao();
        
        final List<Note> allNotes = noteDao.readNotes();
        assertNotNull(allNotes, "Result of readNotes() should never be null.");
        assertTrue(allNotes.isEmpty(), "There should be no notes.");
    }

    /**
     * Test the note DAO's behavior when reading all existing notes.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesEmptyTest"})
    public void readNotesTest() {
        // TODO implement.
    }
}
