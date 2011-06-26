/**
 * Kuebiko - AbstractNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.testng.TestException;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jcap.model.Note.State;
import com.jcap.util.Pair;

/**
 * Base class for testing classes that implement the NoteDao interface. Tests 
 * basic functionality that all note DAOs should have.
 * @see com.jcap.model.NoteDao
 *
 * @author davehuffman
 */
public abstract class AbstractNoteDaoTest {
    private static final String DUMMY_NOTE_TITLE = "foobar";

    /**
     * Helper method; create dummy notes for use in testing.
     * @param title The title of the new dummy note.
     * @return A newly instantiated dummy note with the passed title.
     */
    private final Note newDummyNote(String title) {
        Note dummyNote = new Note();
        dummyNote.setTitle(title);
        dummyNote.setText("Lorem ipsum.");
        return dummyNote;
    }
    
    /**
     * Helper method; create notes and a DAO for accessing them.
     * @param noteCount The number of notes to create.
     * @return A DAO for accessing the created notes.
     */
    private final NoteDao saveDummyNotes(int noteCount)
    throws ValidationException, IOException {
        final NoteDao noteDao = newNoteDao();
        for (int i = 0; i < noteCount; i++) {
            noteDao.addNote(
                    newDummyNote(String.format("%s [%s]", DUMMY_NOTE_TITLE, i)));
        }
        return noteDao;
    }
    
    /**
     * Helper method; reads all of the notes in the data store and insures the 
     * integrity of the data.
     * @param noteDao The DAO to use in reading the notes.
     * @param expectedNoteCount The expected number of notes in the data store.
     * @return All of the notes in the data store.
     */
    private final List<Note> checkIntegrity(NoteDao noteDao, int expectedNoteCount) 
    throws IOException {
        final List<Note> allNotes = noteDao.readNotes();
        assertNotNull(allNotes, "Result of readNotes() should never be null.");
        assertEquals(allNotes.size(), expectedNoteCount,
                "Note count should equal expected value.");
        
        boolean allClean = true;
        Set<Integer> ids = Sets.newHashSet();
        Set<String> titles = Sets.newHashSet();
        for (Note note: allNotes) {
            allClean = (allClean && note.getState() == State.CLEAN);
            ids.add(note.getId());
            titles.add(note.getTitle());
        }
        
        assertTrue(allClean, "Notes should be clean.");
        assertEquals(ids.size(), allNotes.size(), "Notes should have unique IDs.");
        assertFalse(ids.contains(0), "No note should have an ID of zero.");
        assertEquals(allNotes.size(), titles.size(), 
                "Notes should have unique titles.");
        return allNotes;
    }
    
    /**
     * @return A new instance of a note DAO.
     */
    abstract NoteDao newNoteDao();

    /**
     * Test the note DAO's behavior when adding a new note.
     */
    @Test(dependsOnMethods = "readNotesEmptyTest")
    public void addNoteTest() throws Exception {
        final NoteDao noteDao = newNoteDao();
        final Note dummyNote = newDummyNote(DUMMY_NOTE_TITLE);
        
        new NoteUpdateTestHarness(dummyNote) {
            @Override
            Note performUpdate(Note testNote) throws Exception {
                return noteDao.addNote(testNote);
            }}.runTest();
        
        final List<Note> allNotes = noteDao.readNotes();
        assertNotNull(allNotes, "Result of readNotes() should never be null.");
        assertEquals(allNotes.size(), 1, "There should only be one note.");
        
        final Note onlyNote = Iterables.getOnlyElement(allNotes);
        assertEquals(onlyNote.getTitle(), DUMMY_NOTE_TITLE,
                "Only note should be the one added.");
    }
    
    /**
     * Test the note DAO's behavior when adding two separate notes with the
     * same title. This is not allowed and should fail.
     */
    @Test(dependsOnMethods = {"readNotesEmptyTest", "addNoteTest"}, 
            expectedExceptions = ValidationException.class)
    public void addTwoNotesWithSameTitleTest() throws Exception {
        final NoteDao noteDao = newNoteDao();
        
        noteDao.addNote(newDummyNote(DUMMY_NOTE_TITLE));
        noteDao.addNote(newDummyNote(DUMMY_NOTE_TITLE));
    }
    
    /**
     * Test the note DAO's behavior when deleting an existing note.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesTest"})
    public void deleteNoteTest() throws ValidationException, IOException {
        final int noteCount = 5;
        final NoteDao noteDao = saveDummyNotes(noteCount);
        
        checkIntegrity(noteDao, noteCount);

        final List<Note> allNotes = noteDao.readNotes();
        int deletedNotes = 0;
        for (Note note: ImmutableList.copyOf(allNotes)) {
            final int deletedId = note.getId();
            noteDao.deleteNote(note);
            deletedNotes++;
            
            List<Note> remainingNotes = checkIntegrity(noteDao, 
                    noteCount - deletedNotes);
            assertEquals(remainingNotes.size(), noteCount - deletedNotes, 
                    "Number of notes should be decreased after deletion.");
            for (Note remainingNote: remainingNotes) {
                assertFalse(deletedId == remainingNote.getId(),
                        "Deleted note should no longer exist.");
            }
        }
        assertEquals(deletedNotes, noteCount, "All notes should have been deleted");
        
        checkIntegrity(noteDao, 0);
    }
    
    /**
     * Test the note DAO's behavior when updating an existing note.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesTest"})
    public void updateNoteTest() throws Exception {
        List<Pair<String, String>> changes = Lists.newArrayList();
        changes.add(Pair.of("A New Hope", "The Death Star blows up."));
        changes.add(Pair.of("The Empire Strikes Back", "Luke meets his dad.")); 
        changes.add(Pair.of("Return of the Jedi", "Ewoks sing and dance."));
        
        final int noteCount = 3;
        final NoteDao noteDao = saveDummyNotes(noteCount);
        final List<Note> origNotes = ImmutableList.copyOf(noteDao.readNotes());
        
        for (int i = 0; i < noteCount; i++) {
            final Pair<String, String> change = changes.get(i);
            final Note origNote = origNotes.get(i);
            
            assertFalse(origNote.getTitle().equals(change.first), 
                    "Original title must be different.");
            assertFalse(origNote.getText().equals(change.second), 
                    "Original text must be different.");
            
            final int origId = origNote.getId();
            origNote.setTitle(change.first);
            origNote.setText(change.second);
            
            new NoteUpdateTestHarness(origNote) {
                @Override
                Note performUpdate(Note testNote) throws Exception {
                    return noteDao.updateNote(origNote);
                }}.runTest();
            
            Note updatedNote = null;
            for (Note note: noteDao.readNotes()) {
                if (origId == note.getId()) {
                    updatedNote = note;
                    break;
                }
            }
            
            // Could use Assert.assertNotNull(...), but the manual null check 
            // eliminates the warning for possible null pointer dereference.
            if (updatedNote == null) {
                throw new TestException("ID should still exist.");
            }
            
            assertEquals(updatedNote.getTitle(), change.first, 
                    "Note should have new title.");
            assertEquals(updatedNote.getText(), change.second, 
                    "Note should have new text.");
            
            checkIntegrity(noteDao, noteCount);
        }
    }
    
    /**
     * Test the note DAO's behavior when no notes exist.
     */
    @Test
    public void readNotesEmptyTest() throws IOException {
        checkIntegrity(newNoteDao(), 0);
    }

    /**
     * Test the note DAO's behavior when reading all existing notes.
     */
    @Test(dependsOnMethods = {"addNoteTest", "readNotesEmptyTest"})
    public void readNotesTest() throws ValidationException, IOException {
        int noteCount = 10;
        NoteDao noteDao = saveDummyNotes(noteCount);
        
        checkIntegrity(noteDao, noteCount);
    }
}
