package com.jcap.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/** XXX development helper; not to be included in production code. */
public class NoteDaoDevelopment implements NoteDao {
    /** XXX scaffolding. */
    private static final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    
    /** XXX scaffolding. */
    private static final List<Note> notes = Lists.newArrayList();
    static {
        notes.add(new Note(1, "Luke Skywalker", loremIpsum, new Date(), new Date()));
        notes.add(new Note(2, "Han Soloe", loremIpsum, new Date(), new Date()));
        notes.add(new Note(3, "Jabba the Hutt", loremIpsum, new Date(), new Date()));
        notes.add(new Note(4, "Princess Leia", loremIpsum, new Date(), new Date()));
        notes.add(new Note(5, "Darth Vader", loremIpsum, new Date(), new Date()));
        notes.add(new Note(6, "Yoda", loremIpsum, new Date(), new Date()));
        notes.add(new Note(7, "C3PO", loremIpsum, new Date(), new Date()));
        notes.add(new Note(8, "Chewbacca", loremIpsum, new Date(), new Date()));
    }
    
    @Override
    public void addNote(Note newNote) {
        Preconditions.checkArgument(newNote.isNew());
    }
    
    @Override
    public void deleteNote(Note note) {
        Preconditions.checkArgument(!note.isNew());
    }

    @Override
    public void updateNote(Note note) {
        Preconditions.checkArgument(!note.isNew());
    }
    
//    @Override
//    public List<Note> searchNotes() {
//        return searchNotes(null);
//    }
//
//    @Override
//    public List<Note> searchNotes(final String searchString) {
//        if (StringUtils.isBlank(searchString)) {
//            return Collections.unmodifiableList(notes);
//        }
//        
//        return ImmutableList.copyOf(Collections2.filter(notes, 
//                new Predicate<Note>() {
//                    @Override
//                    public boolean apply(Note note) {
//                        return note.getTitle().contains(searchString)
//                                || note.getTags().contains(searchString)
//                                || note.getText().contains(searchString);
//                    }
//                }));
//    }
}
