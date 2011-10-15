/**
 * Kuebiko - FileSystemNoteCache.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model.filesystem;

import java.io.File;
import java.util.Collection;
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
     * The state of the data in the cache.
     */
    private static enum State { 
        /** The cache is up-to-date with the data in the file system. */
        CURRENT, 
        /** The cache is empty and has not been loaded with data (the initial state). */
        EMPTY, 
        /** The cache has been reset, meaning it once contained data but needs 
         *  to be reloaded from the file system. */
        RESET
    }
    
    /**
     * Immutable value object for representing a note and its corresponding 
     * storage file in the cache.
     */
    private static class CacheItem {
        final File file;
        final Note note;
        
        public CacheItem(File file, Note note) {
            this.file = file;
            this.note = note;
        }
    }
    
    private final static Function<CacheItem, Note> NOTE_FN = 
            new Function<CacheItem, Note>() {
                @Override
                public Note apply(CacheItem input) {
                    return input.note;
                }};
    
    private State state = State.EMPTY;
    private final Map<Integer, CacheItem> data;
    
    /**
     * Instantiate a new file system note cache.
     * @param The initial number of items in the cache.
     */
    public FileSystemNoteCache(int size) {
        data = Maps.newHashMapWithExpectedSize(size);
    }

    /**
     * Reset the data in the cache. Subsequent calls to read data from the cache
     * will cause it to be refilled with data from the file system.
     */
//    void reset() {
//        state = State.RESET;
//        data.clear();
//    }
    
//    boolean isEmpty() {
//        // Don't rely on the number of items in the cache, as it could by 
//        // physically empty after a client calls reset().
//        return state == State.EMPTY;
//    }

    /**
     * Add a note to the cache. 
//     * This method may not be called after resetting 
//     * the cache and before reloading its contents.
     * @param id The note's unique ID.
     * @param noteFile The file representing the note.
     * @param note The note itself.
     */
    void put(int id, File noteFile, Note note) {
//        if (state == State.RESET) {
//            throw new IllegalStateException(
//                    "Data cannot be added immediately after a reset");
//        }
        
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
//     * Retrieve all of the notes in the cache. If the cache has been reset, 
//     * the notes will be loaded from the file system.
     * @return All of the notes in the cache.
     */
    Collection<Note> getNotes() {
        return Collections2.transform(data.values(), NOTE_FN);
    }
}
