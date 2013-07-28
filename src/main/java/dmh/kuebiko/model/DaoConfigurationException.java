/**
 * Kuebiko - DaoConfigurationException.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
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
