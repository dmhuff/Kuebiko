/**
 * Kuebiko - PersistenceException.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
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
