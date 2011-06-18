/**
 * jCap - NoteManagerFactory.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.model;

/**
 * Factory object for note data access objects.
 *
 * @author davehuffman
 */
public final class NoteDaoFactory {
    private NoteDaoFactory() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
    public static NoteDao get() {
        return new NoteDaoDevelopment();
    }
}
