/**
 * Kuebiko - NoteFileUtil.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.model.filesystem;

import java.io.File;
import java.io.FileFilter;

import dmh.kuebiko.model.Note;

/**
 * Utility class for dealing with note files.
 *
 * @author davehuffman
 */
final class NoteFileUtil {
    private NoteFileUtil() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
    static final String FILE_EXTENSION = "html";
    static final String NEW_LINE = System.getProperty("line.separator");
    
    /**
     * Helper method; convert a file's name to the title of the note it represents.
     * @param fileName A file name string. May not be null.
     * @return The converted note title.
     */
    static String fileNameToNoteTitle(String fileName) {
        return fileName.replace("." + FILE_EXTENSION, "");
    }
    
    /**
     * Helper method; convert a note's title to the name of the file where it's
     * data is stored.
     * @param noteTitle A note title string; May not be null.
     * @return The converted file name.
     */
    static String noteTitleToFileName(String noteTitle) {
        return noteTitle + "." + FILE_EXTENSION;
    }
    
    /**
     * Helper method; get the file object for a note.
     * @param noteDir Directory where note files live.
     * @param note The note in question.
     * @return The passed's note file.
     */
    static File getNoteFile(File noteDir, Note note) {
        return new File(noteDir, noteTitleToFileName(note.getTitle()));
    }
    
    static File[] listNoteFilesInDir(File noteDir) {
        return noteDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches(".+?\\." + FILE_EXTENSION);
            }});
    }
}
