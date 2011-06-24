package com.jcap.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/** XXX development helper; not to be included in production code. */
public class NoteDaoMemory implements NoteDao {
    private final List<Note> notes = Lists.newArrayList();;
    
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
    
    @Override
    public List<Note> readNotes() {
        return Collections.unmodifiableList(notes);
    }

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
