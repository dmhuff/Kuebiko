/**
 * Kuebiko - Callback.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.util;

/**
 * Interface for objects implementing a callback method.
 *
 * @author davehuffman
 */
public interface Callback<T> {
    /**
     * Invoke a callback.
     * @param input Input data to the callback. May be null.
     */
    public void callback(T input);
}
