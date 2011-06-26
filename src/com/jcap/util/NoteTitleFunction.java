/**
 * jCap - NoteTitleFuction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.util;

import com.google.common.base.Function;
import com.jcap.model.Note;

/**
 * Singleton function class for extracting a note's title.
 *
 * @author davehuffman
 */
public class NoteTitleFunction implements Function<Note, String> {
    /** Singleton instance of this class. */
    private static final NoteTitleFunction INSTANCE = new NoteTitleFunction();
    
    /**
     * @return An instance of the note title function.
     */
    public static NoteTitleFunction getInstance() {
        return INSTANCE;
    }
    
    private NoteTitleFunction() {}
    
    @Override
    public String apply(Note input) {
        return input.getTitle();
    }
}
