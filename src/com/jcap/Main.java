/**
 * jCap - Kuebiko.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap;

import java.awt.EventQueue;
import java.util.Date;

import com.jcap.controller.NoteManager;
import com.jcap.model.Note;
import com.jcap.model.NoteDao;
import com.jcap.model.NoteDaoFactory;
import com.jcap.model.NoteDaoMemory;
import com.jcap.view.NoteFrame;

/**
 * Main class for the Kuebiko application.
 *
 * @author davehuffman
 */
public class Main {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    /** XXX scaffolding. */
                    NoteDao noteDao = NoteDaoFactory.get(NoteDaoMemory.class.getSimpleName());
                    fillNoteDaoWithDummyData(noteDao);
                    NoteManager noteMngr = new NoteManager(noteDao);
                    NoteFrame noteFrame = new NoteFrame(noteMngr);
                    noteFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** XXX scaffolding. */
    private static void fillNoteDaoWithDummyData(NoteDao dao) {
        final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        dao.addNote(newDummyNote("Luke Skywalker", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Han Solo", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Jabba the Hutt", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Princess Leia", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Darth Vader", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Yoda", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("C3PO", loremIpsum, new Date(), new Date()));
        dao.addNote(newDummyNote("Chewbacca", loremIpsum, new Date(), new Date()));
    }
    
    /** XXX scaffolding. */
    private static Note newDummyNote(String title, String text, Date createDate, Date modifiedDate) {
        Note dummyNote = new Note();
        
        dummyNote.setTitle(title);
        dummyNote.setText(text);
        dummyNote.setCreateDate(createDate);
        dummyNote.setModifiedDate(modifiedDate);
        
        return dummyNote;
    }
}
