/**
 * Kuebiko - DataStoreException.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.controller;

/**
 * Exception thrown when an error occurs during a controller interaction with 
 * the data store.
 * XXX should this be checked or unchecked? 
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
