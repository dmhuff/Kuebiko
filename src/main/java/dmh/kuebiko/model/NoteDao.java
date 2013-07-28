/**
 * Kuebiko - NoteDao.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.model;

import java.util.List;
import java.util.Map;

/**
 * Interface for all note data access objects. Exposes basic CRUD operations 
 * for notes.
 *
 * @author davehuffman
 */
public interface NoteDao {
    /**
     * Initialize the DAO.
     * @param params A map of parameters that will be used to configure the DAO. 
     */
    public void initialize(Map<String, String> params) throws DaoConfigurationException;
    
    /**
     * Add (create) a new note to the data store.
     * @param newNote The note to add.
     * @return The added note.
     */
    public Note addNote(Note newNote) throws ValidationException, PersistenceException;

    /**
     * Delete a note from the data store.
     * @param note The note to delete.
     */
    public void deleteNote(Note deletedNote) throws PersistenceException;

    /**
     * Update a note in the data store with new data.
     * @param note The new note data.
     * @return The updated note.
     */
    public Note updateNote(Note updatedNote) throws PersistenceException;

    /**
     * @return A list of all notes in the data store.
     */
    public List<Note> readNotes() throws PersistenceException;
}
