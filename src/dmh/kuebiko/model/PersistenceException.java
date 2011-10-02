/**
 * Kuebiko - PersistenceException.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

/**
 * Exception signifying a persistence error.
 *
 * @author davehuffman
 */
public class PersistenceException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersistenceException() {}

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
