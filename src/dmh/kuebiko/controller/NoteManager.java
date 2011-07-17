/**
 * Kuebiko - NoteManager.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.controller;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.ValidationException;
import dmh.kuebiko.util.NoteTitleFunction;

/**
 * Management class for notes. Acts as the note controller.
 *
 * @author davehuffman
 */
public class NoteManager {
    private final NoteDao noteDao;
    
    private List<Note> notes = null;

    private final Collection<Note> deletedNotes;
    
    public NoteManager(NoteDao noteDao) {
        this.noteDao = noteDao;
        
        deletedNotes = Lists.newArrayList();
        loadAllNotes();
    }

    private void loadAllNotes() {
        try {
            notes = noteDao.readNotes();
        } catch (IOException e) {
            throw new DataStoreException("Could not read notes.", e);
        }
    }
    
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }
    
    public List<String> getNoteTitles() {
        return Collections.unmodifiableList(
                Lists.transform(notes, NoteTitleFunction.getInstance()));
    }
    
    /**
     * @return True if there are no notes.
     */
    public boolean isEmpty() {
        return notes.isEmpty();
    }
    
    public int getNoteCount() {
        return notes.size();
    }
    
    public Note getNoteAt(int index) {
        return notes.get(index);
    }
    
    public void addNote(Note newNote) {
        notes.add(newNote);
    }
    
    public void deleteNote(Note note) {
        if (!notes.remove(note)) {
            throw new IllegalArgumentException(String.format(
                    "Note [%s] does not exist.", note));
        }
        deletedNotes.add(note);
    }
    
    /**
     * Save any changes made to the notes.
     */
    public void saveAll() {
        try {
            for (Note note: deletedNotes) {
                noteDao.deleteNote(note);
            }
            deletedNotes.clear();
            
            for (Note note: notes) {
                switch (note.getState()) {
//            case DELETED:
//                noteDao.deleteNote(note);
//                break;
                case DIRTY:
                    noteDao.updateNote(note);
                    break;
                case NEW:
                    noteDao.addNote(note);
                    break;
                default:
                    continue;
                }
            }
        } catch (IOException e) {
            throw new DataStoreException("Could not read/write notes.", e);
        } catch (ValidationException e) {
            throw new DataStoreException("Invalid note.", e);
        }
        
        // TODO may not be necessary.
        loadAllNotes();
    }
}
