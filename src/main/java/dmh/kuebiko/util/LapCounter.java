/**
 * Kuebiko - Counter.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.util;

/**
 * Object simulating a lap counter, encapsulating the count to prevent 
 * unwanted modification.
 *
 * @author davehuffman
 */
public class LapCounter {
    private int count = 0;
    
    /**
     * Increment the counter.
     * @return The new count.
     */
    public int tick() {
        return ++count;
    }
    
    /**
     * @return The current count.
     */
    public int getCount() {
        return count;
    }
}
