/**
 * Kuebiko - NoteManagerFactory.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import java.util.List;

import com.google.common.collect.Lists;

import dmh.kuebiko.util.BadClassException;

/**
 * Factory object for note data access objects.
 *
 * @author davehuffman
 */
public final class NoteDaoFactory {
    private NoteDaoFactory() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
    /** A list of all official and supported note DAOs.  */
    private static final List<Class<? extends NoteDao>> OFFICIAL_DAOS = 
            Lists.newArrayList();
    static {
        OFFICIAL_DAOS.add(NoteDaoMemory.class);
    }
    
    @Deprecated
    public static NoteDao get() {
        return new NoteDaoMemory();
    }
    
    /**
     * Retrieve an instance of a note DAO.
     * @param className The name of the note DAO's class.
     * @return An instance of the desired note DAO.
     * @throws BadClassException If the requested class is not an officially 
     *                           supported DAO and it cannot be instantiated.
     */
    public static NoteDao get(String className) throws BadClassException {
        // First try to find the class among the official DAOs.
        try {
            for (Class<? extends NoteDao> clazz: OFFICIAL_DAOS) {
                if (className.equals(clazz.getSimpleName())) {
                    return get(clazz);
                }
            }
        } catch (BadClassException e) {
            // This indicates a programming error, so throw an unchecked exception.
            throw new IllegalArgumentException(String.format(
                    "Class [%s] is a known class, but it cannot be instantiated.", 
                    className), e);
        }
        
        // Now try reflection to find the class, and throw a checked exception 
        // if it cannot be instantiated (which should indicate a configuration 
        // issue, which may be fixable).
        try {
            @SuppressWarnings("unchecked")
            Class<? extends NoteDao> clazz = 
                    (Class<? extends NoteDao>) Class.forName(className);
            return get(clazz);
        } catch (BadClassException bce) {
            throw bce;
        } catch (Exception e) {
            throw new BadClassException(e);
        }
    }
    
    private static <T extends NoteDao> T get(Class<T> clazz) 
    throws BadClassException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new BadClassException(e);
        } catch (IllegalAccessException e) {
            throw new BadClassException(e);
        }
    }
}
