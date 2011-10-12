/**
 * Kuebiko - NoteLazyLoader.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

/**
 * Interface for objects capable of lazy loading note data.
 *
 * @author davehuffman
 */
public interface NoteTextLazyLoader {
    /**
     * Load and return the text of a note object. The calling client is 
     * responsible for setting the note's text field.
     * @param note The note to load. May not be null. Will not be modified.
     * @return The text of the passed note.
     */
    public String loadText(Note note) throws PersistenceException;
}
