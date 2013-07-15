/**
 * kuebiko - LazyInMemoryNoteDao.java
 * Copyright 2012 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public class LazyInMemoryNoteDao extends InMemoryNoteDao implements NoteTextLazyLoader {
    private Map<Integer, String> cache;

    @Override
    public List<Note> readNotes() {
        cache = Maps.newHashMap();
        
        List<Note> notes = Lists.newArrayList();
        for (Note note : super.readNotes()) {
            cache.put(note.getId(), note.getText());
            notes.add(new Note(note.getId(), note.getTitle(), 
                    note.getCreateDate(), note.getModifiedDate(), this));
        }
        this.notes.clear();
        this.notes.addAll(notes);
        
        return Collections.unmodifiableList(notes);
    }
    
    @Override
    public String loadText(Note note) throws PersistenceException {
        if (!cache.containsKey(note.getId())) {
            throw new IllegalArgumentException(
                    String.format("Note id [%d] doesn't exist.", note.getId()));
        }
        return cache.get(note.getId());
    }

}
