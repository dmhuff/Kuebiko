/**
 * Kuebiko - InMemoryNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Note data access object (DAO) for storing notes in memory.
 *
 * @author davehuffman
 */
public class InMemoryNoteDao extends AbstractNoteDao {
    private int noteCount = 0;
    /** A list of notes, which acts as a data store for notes. */
    protected final List<Note> notes = Lists.newArrayList();
    
    @Override
    protected Note findNote(int id) {
        for (Note note: notes) {
            if (id == note.getId()) {
                return note;
            }
        }
        return null;
    }
    
    @Override
    protected Note findNote(String title) {
        for (Note note: notes) {
            if (title.equals(note.getTitle())) {
                return note;
            }
        }
        return null;
    }
    
    @Override
    protected int getUniqueId() {
        return ++noteCount;
    }

    @Override
    public Note persistActionAdd(Note addedNote) {
        notes.add(addedNote);
        return addedNote;
    }
    
    @Override
    public void persistActionDelete(Note deletedNote) {
        notes.remove(deletedNote);
    }

    @Override
    public Note persistActionUpdate(Note updatedNote) {
        notes.set(findNoteIndex(updatedNote), updatedNote);
        return updatedNote;
    }
    
    /**
     * Helper method. Find the index of a particular note within the internal 
     * note list ({@link InMemoryNoteDao#notes}).
     * @param noteToFind The note to find. Must exist in the internal note list.
     * @return The index of the found note.
     */
    private int findNoteIndex(Note noteToFind) {
        int index = 0;
        for (Note note: notes) {
            if (noteToFind.getId() == note.getId()) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException(String.format(
                "Note [%s] does not exist.", noteToFind));
    }
    
    @Override
    public List<Note> readNotes() {
        return Collections.unmodifiableList(notes);
    }
}
