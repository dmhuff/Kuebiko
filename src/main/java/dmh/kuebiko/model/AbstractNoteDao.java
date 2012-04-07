/**
 * Kuebiko - AbstractNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Abstract class for note data access objects (DAOs). This class contains 
 * validations for notes and defers the actual persistence of note data to 
 * client classes.
 *
 * @author davehuffman
 */
public abstract class AbstractNoteDao implements NoteDao {
    private final Set<DaoParameter> requiredParameters;
    
    protected Map<String, String> params;
    
    protected AbstractNoteDao() {
        this(null);
    }
    
    protected AbstractNoteDao(Set<DaoParameter> requiredParameters) {
        this.requiredParameters = requiredParameters;
    }
    
    @Override
    public final void initialize(Map<String, String> params) 
    throws DaoConfigurationException {
        this.params = params;
        checkParameters();
        
        postInitialize();
    }
    
    /**
     * Perform any custom initialization that needs to happen after 
     * {@link #initialize(Map)}.
     * @throws DaoConfigurationException May be thrown by client subclasses.
     */
    protected void postInitialize() throws DaoConfigurationException {
        // By default, do nothing.
    }

    /**
     * Ensure that this DAO's required parameters have been supplied.
     * @param params The parameters to check.
     */
    protected final void checkParameters() 
    throws DaoConfigurationException {
        if (requiredParameters == null || requiredParameters.isEmpty()) {
            // If nothing is required, then there's no need to go forward.
            return;
        }
        
        if (params == null) {
            throw new DaoConfigurationException(
                    "Parameters are required but none were provided.");
        }
        
        for (DaoParameter reqParam: requiredParameters) {
            if (getParameter(reqParam) == null) {
                throw new DaoConfigurationException(
                        String.format("Parameter [%s] is required.", reqParam));
            }
        }
    }
    
    /**
     * Retrieve a parameter value by its key.
     * @param paramKey A parameter key.
     * @return The parameter mapped to the passed key, or null if none exists.
     */
    protected final String getParameter(DaoParameter paramKey) {
        return DaoParameter.getParameter(params, paramKey);
    }
    
    /**
     * Note entity factory. Creates a new note.
     * @param title The note's title.
     * @param createDate The note's create date.
     * @param modifiedDate The note's modified date.
     * @param loader A lazy loader for the note data.
     * @return A new note from the passed data.
     */
    protected final Note newNote(String title, Date createDate, Date modifiedDate, 
            NoteTextLazyLoader loader) {
        return new Note(getUniqueId(), title, createDate, modifiedDate, loader);
    }
    
    /**
     * Note entity factory. Makes a hollow copy of a source note.
     * @param source The note to copy.
     * @param loader A lazy loader for the new hollow note.
     * @return A hollow copy of the passed note.
     */
    protected final Note copyNote(Note source, NoteTextLazyLoader loader) {
        return new Note(source.getId(), source.getTitle(), 
                source.getCreateDate(), source.getModifiedDate(), loader);
    }

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
    public final Note addNote(Note newNote) 
    throws ValidationException, PersistenceException {
        Preconditions.checkArgument(newNote.isNew());
        
        if (!isTitleUnique(newNote.getTitle())) {
            throw new ValidationException(String.format(
                    "A note with title [%s] already exists.", newNote.getTitle()));
        }
        
        Note addedNote = new Note(getUniqueId(), newNote);
        addedNote.setModifiedDate(new Date());
        addedNote.reset();
        
        return persistActionAdd(addedNote);
    }
    
    /**
     * @return An ID that is unused by any existing note.
     */
    protected abstract int getUniqueId();

    /**
     * Persist a new note to the data store.
     * @param addedNote The new note to persist.
     * @return The added note.
     * @throws PersistenceException 
     */
    protected abstract Note persistActionAdd(Note addedNote) 
    throws PersistenceException;

    @Override
    public final void deleteNote(Note deletedNote) 
    throws PersistenceException {
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
     * @throws PersistenceException 
     */
    protected abstract void persistActionDelete(Note deletedNote) 
    throws PersistenceException;

    @Override
    public final Note updateNote(Note note) throws PersistenceException {
        Preconditions.checkArgument(!note.isNew());
        Preconditions.checkArgument(!note.isHollow());
        Preconditions.checkArgument(note.isDirty());
        Preconditions.checkNotNull(findNote(note.getId()), 
                String.format("Note [%s] not found.", note));
        
        note.setModifiedDate(new Date());
        note = persistActionUpdate(note);
        note.reset();
        return note;
    }
    
    /**
     * Update an existing note in the data store.
     * @param updatedNote The note to update.
     * @return The updated note.
     */
    protected abstract Note persistActionUpdate(Note updatedNote)
    throws PersistenceException;
}
