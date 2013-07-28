/**
 * Kuebiko - NoteTitleFuction.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.util;

import com.google.common.base.Function;

import dmh.kuebiko.model.Note;

/**
 * Singleton function class for extracting a note's title.
 *
 * @author davehuffman
 */
public final class NoteTitleFunction implements Function<Note, String> {
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
