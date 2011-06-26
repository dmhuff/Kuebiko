/**
 * Kuebiko - AbstractNoteDaoTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jcap.util.NoteTitleFunction;
import com.jcap.util.Pair;

/**
 * Note data access object for storing notes in memory.
 *
 * @author davehuffman
 */
public class NoteDaoMemory implements NoteDao {
    private final List<Note> notes = Lists.newArrayList();
    private int noteCount = 0;
    
    /**
     * Find a note and its index by the note's ID.
     * @param id The ID of the note to find.
     * @return A pair value object for found note, where the first value is the 
     *         note's index and the second is the note, or null if the note 
     *         does not exist.
     */
    private Pair<Integer, Note> findNoteWithIndex(int id) {
        int index = 0;
        for (Note note: notes) {
            if (id == note.getId()) {
                return Pair.of(index, note);
            }
            index++;
        }
        return null;
    }
    
    /**
     * Find a note by its ID.
     * @param id The ID of the note to find.
     * @return The found note, or null if it does not exist.
     */
    private Note findNote(int id) {
        Pair<Integer, Note> found = findNoteWithIndex(id);
        return (found == null)? null : found.second;
    }
    
    @Override
    public Note addNote(Note newNote) throws ValidationException {
        Preconditions.checkArgument(newNote.isNew());
        
        if (Lists.transform(notes, NoteTitleFunction.getInstance()).contains(
                        newNote.getTitle())) {
            throw new ValidationException(String.format(
                    "A note with title [%s] already exists.", newNote.getTitle()));
            
        }
        
        Note addedNote = new Note(++noteCount, newNote);
        addedNote.setModifiedDate(new Date());
        addedNote.reset();
        notes.add(addedNote);
        return addedNote;
    }
    
    @Override
    public void deleteNote(Note deletedNote) {
        Preconditions.checkArgument(!deletedNote.isNew());
        
        final Note foundNote = findNote(deletedNote.getId());
        if (foundNote == null) {
            throw new IllegalArgumentException(String.format(
                    "Passed note [%s] does not exist.", deletedNote));
        }
        notes.remove(foundNote);
    }

    @Override
    public Note updateNote(Note note) {
        Preconditions.checkArgument(!note.isNew());
        Preconditions.checkArgument(note.isDirty());
        
        Pair<Integer, Note> foundNotePair = findNoteWithIndex(note.getId());
        if (foundNotePair == null) {
            throw new IllegalArgumentException(
                    String.format("Note [%s] not found.", note));
        }
        notes.remove(foundNotePair.first.intValue());
        final Note updatedNote = new Note(foundNotePair.second.getId(), note);
        updatedNote.setModifiedDate(new Date());
        updatedNote.reset();
        notes.add(foundNotePair.first, updatedNote);
        
        return updatedNote;
    }
    
    @Override
    public List<Note> readNotes() {
        return Collections.unmodifiableList(notes);
    }
}
