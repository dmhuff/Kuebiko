/**
 * Kuebiko - Pair.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.util;

import java.io.Serializable;

/**
 * Value object representing an immutable pair of values.
 * Based on code in "Java Needs to Get a Pair (and a Triple...)", by Dick Wall:
 * {@link http://www.developer.com/java/ejb/article.php/3813031/Java-Needs-to-Get-a-Pair-and-a-Triple.htm}
 * @author davehuffman
 */
public final class Pair<E1, E2> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static <E1, E2> Pair<E1, E2> of (E1 first, E2 second) {
        return new Pair<E1, E2>(first, second);
    }

    public final E1 first;
    public final E2 second;

    private Pair (E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }
}
