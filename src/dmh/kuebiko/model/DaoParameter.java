/**
 * Kuebiko - DaoParameter.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * An enumeration of keys for DAO configuration parameters.
 *
 * @author davehuffman
 */
public enum DaoParameter {
    /** A directory in the local file system where note data can be found. */
    DIRECTORY;
    
    /**
     * Retrieve a string parameter value from a parameter map.
     * @param paramMap The map containing the desired value.
     * @param paramKey The key of the desired value.
     * @return The requested value, or null if it does not exist.
     */
    public static String getParam(Map<String, String> paramMap, DaoParameter paramKey) {
        return StringUtils.trimToNull(paramMap.get(paramKey.toString()));
    }
}
