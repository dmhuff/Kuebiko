/**
 * jCap - NoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;

import java.util.List;

/**
 * Interface for all note data access objects. Exposes basic CRUD operations 
 * for notes.
 *
 * @author davehuffman
 */
public interface NoteDao {
    /**
     * Add (create) a new note to the data store.
     * @param newNote The note to add.
     */
    public void addNote(Note newNote);

    /**
     * Delete a note from the data store.
     * @param note The note to delete.
     */
    public void deleteNote(Note note);

    /**
     * Update a note in the data store with new data.
     * @param note The new note data.
     */
    public void updateNote(Note note);

    /**
     * @return A list of all notes in the data store.
     */
    public List<Note> readNotes();

//    /**
//     * @return A list of all notes in the data store.
//     */
//    public List<Note> searchNotes();
//
//    /**
//     * Retrieve notes from the data store based using a search string.
//     * @param searchString The string to apply to all returned notes.
//     * @return A list of notes matching the passed search string.
//     */
//    public List<Note> searchNotes(final String searchString);

}