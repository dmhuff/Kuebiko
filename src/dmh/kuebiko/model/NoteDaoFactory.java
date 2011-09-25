/**
 * Kuebiko - NoteManagerFactory.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import com.google.common.base.Preconditions;

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
    
    /** Enumeration of all official and supported note DAOs.  */
    public static enum OfficialDao {
        IN_MEMORY(InMemoryNoteDao.class),
        FILE_SYSTEM(FileSystemNoteDao.class);
        
        private final Class<? extends NoteDao> clazz;
        
        private OfficialDao(Class<? extends NoteDao> clazz) {
            this.clazz = clazz;
        }
    }
    
    /**
     * Retrieve an instance of a note DAO.
     * @param className The name of the note DAO's class.
     * @return An instance of the desired note DAO.
     * @throws BadClassException If the requested class is not an officially 
     *                           supported DAO and it cannot be instantiated.
     */
    public static NoteDao get(String className) throws BadClassException {
        Preconditions.checkNotNull(className);
        
        // First try to find the class among the official DAOs.
        try {
            return get(Enum.valueOf(OfficialDao.class, className));
        } catch (BadClassException bce) {
            // This indicates a programming error, so throw an unchecked exception.
            throw new IllegalArgumentException(String.format(
                    "Class [%s] is a known class, but it cannot be instantiated.", 
                    className), bce);
        } catch (Exception ignore) {
            // There was a problem getting an official DAO from the enumeration. 
            // Now try reflection to find the class, and throw a checked 
            // exception if it cannot be instantiated (which should indicate a 
            // configuration issue, and may be fixable).
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
    }
    
    /**
     * Retrieve an instance of an official note DAO.
     * @param daoEnum Enumeration value of the requested DAO.
     * @return An instance of the desired note DAO.
     */
    public static NoteDao get(OfficialDao daoEnum) throws BadClassException {
        return get(daoEnum.clazz);
    }
    
    /**
     * Helper method. Instantiates and instance of a NoteDao class and wraps 
     * any resulting errors in a custom checked exception.
     * @param clazz The NoteDao class to instantiate.
     * @return An instance of the passed class.
     * @throws BadClassException If there in an instantiation error. 
     */
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
