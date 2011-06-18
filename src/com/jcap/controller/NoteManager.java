package com.jcap.controller;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jcap.model.Note;
import com.jcap.model.NoteDao;
import com.jcap.model.NoteDaoFactory;

public class NoteManager {
    @SuppressWarnings("unused")
    private static final Function<Note, String> TITLE_TRANSFORMER =
            new Function<Note, String>() {
                @Override
                public String apply(Note input) {
                    return input.getTitle();
                }
            };
            
    private final Predicate<Note> searchFilter = new Predicate<Note>() {
                @Override
                public boolean apply(Note note) {
                    return note.getTitle().contains(filter)
                            || note.getTags().contains(filter)
                            || note.getText().contains(filter);
                }
            };
    
    private final NoteDao noteDao = NoteDaoFactory.get();
    
    private final List<Note> notes;
    private String filter;

    private final Collection<Note> deletedNotes;
    
    public NoteManager() {
        notes = noteDao.readNotes();
        deletedNotes = Lists.newArrayList();
    }
    
    public List<Note> getNotes() {
        return isBlank(filter)? Collections.unmodifiableList(notes)
                : ImmutableList.copyOf(Collections2.filter(notes, searchFilter));
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
    
    public void saveAll() {
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
    }
    
    public String getFilter() {
        return filter;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
    }
}
