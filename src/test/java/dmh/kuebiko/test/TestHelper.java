/**
 * Kuebiko - TestHelper.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.test;

import org.testng.TestException;

import dmh.kuebiko.controller.NoteManager;
import dmh.kuebiko.model.InMemoryNoteDao;
import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.NoteDaoFactory;
import dmh.kuebiko.model.PersistenceException;
import dmh.kuebiko.model.ValidationException;
import dmh.kuebiko.view.NoteTableModel;

/**
 * Utility class for helping with unit tests.
 *
 * @author davehuffman
 */
public final class TestHelper {
    /** Class name of the default note DAO for use in testing. */
    private static final String DEFAULT_TEST_NOTE_DAO = InMemoryNoteDao.class.getName();

    private TestHelper() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
    /**
     * @return A new note table model containing the passed notes.
     */
    public static NoteTableModel newNoteTableModel(Note... notes) {
        NoteManager noteMngr = newNoteManager(notes);
        return new NoteTableModel(noteMngr);
    }

    /**
     * @return A new note manager containing the passed notes.
     */
    public static NoteManager newNoteManager(Note... notes) {
        final NoteDao noteDao;
        try {
            noteDao = NoteDaoFactory.get(DEFAULT_TEST_NOTE_DAO);
        } catch (Exception e) {
            throw new TestException("Couldn't instantiate note DAO.", e);
        }
        try {
            for (Note note: notes) {
                noteDao.addNote(note);
            }
        } catch (Exception e) {
            throw new TestException("Couldn't populate notes.", e);
        }
        NoteManager noteMngr = new NoteManager(noteDao);
        return noteMngr;
    }
    
    public static NoteDao newDummyNoteDao() {
        NoteDao noteDao;
        try {
            noteDao = NoteDaoFactory.get(DEFAULT_TEST_NOTE_DAO);
            fillNoteDaoWithDummyData(noteDao);
            return noteDao;
        } catch (Exception e) {
            throw new TestException("Couldn't create dummy DAO.", e);
        }
    }
    
    private static void fillNoteDaoWithDummyData(NoteDao dao) 
    throws ValidationException, PersistenceException {
        final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        dao.addNote(newDummyNote("Luke Skywalker", loremIpsum));
        dao.addNote(newDummyNote("Han Solo", loremIpsum));
        dao.addNote(newDummyNote("Jabba the Hutt", loremIpsum));
        dao.addNote(newDummyNote("Princess Leia", loremIpsum));
        dao.addNote(newDummyNote("Darth Vader", loremIpsum));
        dao.addNote(newDummyNote("Yoda", loremIpsum));
        dao.addNote(newDummyNote("C3PO", loremIpsum));
        dao.addNote(newDummyNote("Chewbacca", loremIpsum));
    }
    
    public static Note newDummyNote(String title, String text) {
        Note dummyNote = new Note();
        
        dummyNote.setTitle(title);
        dummyNote.setText(String.format("[%s]\n\n%s", title, text));
        
        return dummyNote;
    }
}
