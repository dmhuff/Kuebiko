/**
 * Kuebiko - UnfoundClassException.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.util;

/**
 * Checked exception thrown when the application cannot handle a request for a
 * particular class. For example, this exception might be thrown when trying
 * to load a missing or invalid plugin.
 *
 * @author davehuffman
 */
public class BadClassException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadClassException() {
        super();
    }

    public BadClassException(String message) {
        super(message);
    }

    public BadClassException(Throwable cause) {
        super(cause);
    }

    public BadClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
