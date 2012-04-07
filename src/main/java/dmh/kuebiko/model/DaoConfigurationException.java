/**
 * Kuebiko - DaoConfigurationException.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

/**
 * Exception signifying an issue with configuring a DAO.
 *
 * @author davehuffman
 */
public class DaoConfigurationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DaoConfigurationException() {}

    public DaoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoConfigurationException(String message) {
        super(message);
    }

    public DaoConfigurationException(Throwable cause) {
        super(cause);
    }
}
