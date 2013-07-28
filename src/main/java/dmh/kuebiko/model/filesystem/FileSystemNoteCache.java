/**
 * Kuebiko - FileSystemNoteCache.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.model.filesystem;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

import dmh.kuebiko.model.Note;

/**
 * Cache object for note data stored in a single directory within a file system.
 *
 * @author davehuffman
 */
class FileSystemNoteCache {
    /**
     * Immutable value object for representing a note and its corresponding 
     * storage file in the cache.
     */
    private static class CacheItem {
        private final File file;
        private final Note note;
        
        public CacheItem(File file, Note note) {
            this.file = file;
            this.note = note;
        }
    }
    
    /** Function for extracting a note from a cache item. */
    private final static Function<CacheItem, Note> NOTE_FN = 
            new Function<CacheItem, Note>() {
                @Override
                public Note apply(CacheItem input) {
                    return input.note;
                }};
    
    private final Map<Integer, CacheItem> data;
    
    /**
     * Instantiate a new file system note cache.
     * @param The initial number of items in the cache.
     */
    public FileSystemNoteCache(int size) {
        data = Maps.newHashMapWithExpectedSize(size);
    }

    /**
     * Add a note to the cache. 
     * @param id The note's unique ID.
     * @param noteFile The file representing the note.
     * @param note The note itself.
     */
    void put(int id, File noteFile, Note note) {
        data.put(id, new CacheItem(noteFile, note));
    }
    
    /**
     * Remove a note from the cache.
     * @param id The ID of the note to remove.
     */
    public void remove(int id) {
        data.remove(id);
    }
    
    /**
     * Retrieve a note's file by its ID.
     * @param id A note ID.
     * @return The note's file object, or null if none exists for the passed ID.
     */
    File getFile(int id) {
        CacheItem cacheItem = data.get(id);
        return cacheItem == null? null : cacheItem.file;
    }

    /**
     * @return An immutable view of the notes in the cache.
     */
    Collection<Note> getNotes() {
        return Collections.unmodifiableCollection(
                Collections2.transform(data.values(), NOTE_FN));
    }
}
