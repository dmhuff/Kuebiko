/**
 * Kuebiko - NoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import java.io.IOException;
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
     * @return The added note.
     */
    public Note addNote(Note newNote) throws ValidationException, IOException;

    /**
     * Delete a note from the data store.
     * @param note The note to delete.
     */
    public void deleteNote(Note deletedNote) throws IOException;

    /**
     * Update a note in the data store with new data.
     * @param note The new note data.
     * @return The updated note.
     */
    public Note updateNote(Note updatedNote) throws IOException;

    /**
     * @return A list of all notes in the data store.
     */
    public List<Note> readNotes() throws IOException;
}
