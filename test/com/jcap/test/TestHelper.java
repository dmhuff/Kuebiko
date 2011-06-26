/**
 * Kuebiko - TestHelper.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.test;

import org.testng.TestException;

import com.jcap.controller.NoteManager;
import com.jcap.model.Note;
import com.jcap.model.NoteDao;
import com.jcap.model.NoteDaoFactory;
import com.jcap.util.BadClassException;
import com.jcap.view.NoteTableModel;

/**
 * Utility class for helping with unit tests.
 *
 * @author davehuffman
 */
public final class TestHelper {
    /** Class name of the default note DAO for use in testing. */
    private static final String DEFAULT_TEST_NOTE_DAO = "NoteDaoMemory";

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
        } catch (BadClassException e) {
            throw new TestException("Couldn't instantiate new note table mode.l", e);
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
}
