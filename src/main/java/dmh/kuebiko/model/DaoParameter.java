/**
 * Kuebiko - DaoParameter.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.model;

import static org.apache.commons.lang.StringUtils.trimToNull;

import java.util.Map;

/**
 * An enumeration of keys for DAO configuration parameters.
 *
 * @author davehuffman
 */
public enum DaoParameter {
    /** A directory in the local file system where note data can be found. */
    CLASS_NAME, DIRECTORY;
    
    /**
     * Retrieve a string parameter value from a parameter map.
     * @param paramMap The map containing the desired value.
     * @param paramKey The key of the desired value.
     * @return The requested value, or null if it does not exist.
     */
    static String getParameter(Map<String, String> paramMap, DaoParameter paramKey) {
        return trimToNull(paramMap.get(paramKey.toString()));
    }
}
