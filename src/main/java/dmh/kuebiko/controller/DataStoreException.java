/**
 * Kuebiko - DataStoreException.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.controller;

/**
 * Exception thrown when an error occurs during a controller interaction with
 * the data store.
 *
 * @author davehuffman
 */
public class DataStoreException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStoreException(Throwable cause) {
        super(cause);
    }
}
