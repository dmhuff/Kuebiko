/**
 * Kuebiko - FileSystemNoteDao.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model;

import java.io.IOException;
import java.util.List;

/**
 * Note data access object (DAO) for storing notes in the file system.
 *
 * @author davehuffman
 */
public class FileSystemNoteDao extends AbstractNoteDao {

    @Override
    public List<Note> readNotes() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Note findNote(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Note findNote(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Note persistActionAdd(Note addedNote) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void persistActionDelete(Note deletedNote) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected Note persistActionUpdate(Note updatedNote) {
        // TODO Auto-generated method stub
        return null;
    }

}
