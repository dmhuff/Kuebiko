/**
 * Kuebiko - Pair.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Value object representing an immutable pair of values.
 * Based on code in "Java Needs to Get a Pair (and a Triple...)", by Dick Wall:
 * {@link http://www.developer.com/java/ejb/article.php/3813031/Java-Needs-to-Get-a-Pair-and-a-Triple.htm}
 * 
 * @author davehuffman
 */
public final class Pair<E1, E2> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Static factory. Builds a single pair object.
     * @param first The first item in the pair.
     * @param second The second item in the pair.
     * @return A pair object containing the passed values.
     */
    public static <E1, E2> Pair<E1, E2> of(E1 first, E2 second) {
        return new Pair<E1, E2>(first, second);
    }
    
    /**
     * Static factory for pair lists. Builds a list of pairs, where the first
     * two items in the data set are the first and second items in the first 
     * pair, and so on.
     * @param data The data that will comprise the pairs. Must contain an even 
     *             number of items.
     * @return An immutable list of pairs build from the passed data set.
     */
    public static <T> List<Pair<T, T>> list(T... data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Pair list factory requires an even number of arguments.");
        }
        
        List<Pair<T, T>> pairList = Lists.newArrayListWithCapacity(data.length / 2);
        T pairFirst = null;
        for (T datum: data) {
            if (pairFirst == null) {
                pairFirst = datum;
            } else {
                pairList.add(Pair.of(pairFirst, datum));
                pairFirst = null;
            }
        }
        return Collections.unmodifiableList(pairList);
    }

    public final E1 first;
    public final E2 second;

    private Pair(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }
}
