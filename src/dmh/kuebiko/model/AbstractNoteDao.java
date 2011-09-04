/**
 * Kuebiko - AbstractNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import java.util.Date;

import com.google.common.base.Preconditions;

/**
 * Abstract class for note data access objects (DAOs). This class contains 
 * validations for notes and defers the actual persistence of note data to 
 * client classes.
 *
 * @author davehuffman
 */
abstract class AbstractNoteDao implements NoteDao {
    private int noteCount = 0;
    
    /**
     * Find a note by its ID.
     * @param id The ID of the note to find.
     * @return The found note, or null if it does not exist.
     */
    protected abstract Note findNote(int id);

    /**
     * Find a note by its title.
     * @param title The title of the note to find.
     * @return The found note, or null if it does not exist.
     */
    protected abstract Note findNote(String title);
    
    /**
     * Determine if a note already exists with the passed title.
     * @param title The title to check.
     * @return True if the passed title is unique (unused), false otherwise.
     */
    private boolean isTitleUnique(String title) {
        return (findNote(title) == null);
    }
    
    @Override
    public final Note addNote(Note newNote) throws ValidationException {
        Preconditions.checkArgument(newNote.isNew());
        
        if (!isTitleUnique(newNote.getTitle())) {
            throw new ValidationException(String.format(
                    "A note with title [%s] already exists.", newNote.getTitle()));
        }
        
        Note addedNote = new Note(++noteCount, newNote);
        addedNote.setModifiedDate(new Date());
        addedNote.reset();
        
        return persistActionAdd(addedNote);
    }
    
    /**
     * Persist a new note to the data store.
     * @param addedNote The new note to persist.
     * @return The added note.
     */
    protected abstract Note persistActionAdd(Note addedNote);

    @Override
    public final void deleteNote(Note deletedNote) {
        Preconditions.checkArgument(!deletedNote.isNew());
        
        final Note foundNote = findNote(deletedNote.getId());
        if (foundNote == null) {
            throw new IllegalArgumentException(String.format(
                    "Passed note [%s] does not exist.", deletedNote));
        }
        persistActionDelete(deletedNote);
    }

    /**
     * Delete a note from the data store.
     * @param deletedNote The note to delete.
     */
    protected abstract void persistActionDelete(Note deletedNote);

    @Override
    public final Note updateNote(Note note) {
        Preconditions.checkArgument(!note.isNew());
        Preconditions.checkArgument(note.isDirty());
        
        Note foundNote = findNote(note.getId());
        if (foundNote == null) {
            throw new IllegalArgumentException(
                    String.format("Note [%s] not found.", note));
        }
        final Note updatedNote = new Note(note);
        updatedNote.setModifiedDate(new Date());
        updatedNote.reset();
        
        return persistActionUpdate(updatedNote);
    }

    /**
     * Update an existing note in the data store.
     * @param updatedNote The note to update.
     * @return The updated note.
     */
    protected abstract Note persistActionUpdate(Note updatedNote);
}
