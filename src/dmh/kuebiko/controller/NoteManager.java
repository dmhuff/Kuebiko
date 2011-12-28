/**
 * Kuebiko - NoteManager.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.model.Note.State;
import dmh.kuebiko.model.NoteDao;
import dmh.kuebiko.model.PersistenceException;
import dmh.kuebiko.model.ValidationException;
import dmh.kuebiko.util.NoteTitleFunction;

/**
 * Management class for notes. Acts as the note controller.
 *
 * @author davehuffman
 */
public class NoteManager {
    static final String DEFAULT_NOTE_TITLE = "Untitled Note"; // TODO i18n.
    
    private final NoteDao noteDao;
    
    private List<Note> notes = null;
    private final Collection<Note> deletedNotes;
    private boolean unsavedChanges = false;
    
    /**
     * Constructor.
     * @param noteDao The DAO to use for note persistence.
     */
    public NoteManager(NoteDao noteDao) {
        this.noteDao = noteDao;
        
        deletedNotes = Lists.newArrayList();
        loadAllNotes();
    }

    private void loadAllNotes() {
        try {
            notes = Lists.newArrayList(noteDao.readNotes());
            unsavedChanges = false;
        } catch (PersistenceException e) {
            throw new DataStoreException("Could not read notes.", e);
        }
    }
    
    /**
     * @return An immutable view of all notes in the stack.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }
    
    /**
     * @return A view containing the titles for all notes in the stack.
     */
    public List<String> getNoteTitles() {
        return Lists.transform(notes, NoteTitleFunction.getInstance());
    }
    
    /**
     * @return True if there are no notes.
     */
    public boolean isEmpty() {
        return notes.isEmpty();
    }
    
    public boolean doesNoteExist(String title) {
        return getNoteTitles().contains(title);
    }
    
    public int getNoteCount() {
        return notes.size();
    }
    
    public Note getNoteAt(int index) {
        return notes.get(index);
    }
    
    /**
     * Determine if there are changes that have not been saved to the data store.
     * @return True if there are unsaved changes.
     */
    public boolean hasUnsavedChanges() {
        // First check the cached value.
        if (unsavedChanges) {
            return unsavedChanges;
        }
        // A false value does not necessarily indicate to changes are present; 
        // we need to check each note to determine if it has been updated.
        for (Note note : notes) {
            if (note.getState() == State.DIRTY) {
                unsavedChanges = true;
                break;
            }
        }
        return unsavedChanges;
    }
    
    /**
     * Add a new note with a default title.
     * @return The title of the new note.
     */
    public String addNewNote() {
        final String title = genUniqueNoteTitle();
        addNewNote(title);
        return title;
    }
    
    /**
     * Generate a unique note title for a new note.
     * @return A note title that is guaranteed to be unique among all notes
     *         in the stack.
     */
    String genUniqueNoteTitle() {
        // Get a list of default note titles.
        final Collection<String> defaultTitles = 
                Collections2.filter(getNoteTitles(), 
                        new Predicate<String>() {
                            @Override
                            public boolean apply(String input) {
                                return input.startsWith(DEFAULT_NOTE_TITLE);
                            }
                        });
        
        if (defaultTitles.isEmpty()) {
            return DEFAULT_NOTE_TITLE;
        }
        
        // Default note titles exist; work thought the list and create a new, 
        // unique default note title.
        Pattern titleRegex = Pattern.compile(DEFAULT_NOTE_TITLE + " (\\d+)");
        int maxSuffix = 1;
        for (String title: defaultTitles) {
            Matcher matcher = titleRegex.matcher(title);
            if (matcher.matches()) {
                int suffix = Integer.parseInt(matcher.group(1));
                if (suffix > maxSuffix) {
                    maxSuffix = suffix;
                }
            }
        }
        String title = String.format("%s %d", DEFAULT_NOTE_TITLE, maxSuffix + 1);
        return title;
    }
    
    public void addNewNote(String title) {
        Note note = new Note();
        note.setTitle(title);
        addNote(note);
    }
    
    void addNote(Note newNote) {
        notes.add(newNote);
        unsavedChanges = true;
    }
    
    public void deleteNote(Note note) {
        if (!notes.remove(note)) {
            throw new IllegalArgumentException(String.format(
                    "Note [%s] does not exist.", note));
        }
        deletedNotes.add(note);
        unsavedChanges = true;
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
        } catch (PersistenceException e) {
            throw new DataStoreException("Could not read/write notes.", e);
        } catch (ValidationException e) {
            throw new DataStoreException("Invalid note.", e);
        }
        
        // XXX may not be necessary.
        loadAllNotes();
    }
}
