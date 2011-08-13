/**
 * jCap - NoteManagerTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.controller;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.test.TestHelper;

/**
 * TestNG test class for the NoteManager controller class.
 * @see dmh.kuebiko.controller.NoteManager
 *
 * @author davehuffman
 */
public class NoteManagerTest {
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void immutableNotesTest() {
        TestHelper.newNoteManager().getNotes().add(new Note());
    }
    
    @Test
    public void addNewNoteWithTitleTest() {
        final NoteManager noteMngr = TestHelper.newNoteManager();
        
        // Fill a list of dummy note titles.
        final int noteCount = 1000;
        final List<String> dummyTitles = Lists.newArrayListWithCapacity(noteCount);
        for (int i = 0; i < noteCount; i++) {
            dummyTitles.add(String.format("foobar-%d", i));
        }
        
        for (String dummyTitle: dummyTitles) {
            noteMngr.addNewNote(dummyTitle);
        }
        
        assertEquals(dummyTitles.size(), noteMngr.getNotes().size(),
                "Note stack should contain same number of added notes.");

        for (String dummyTitle: dummyTitles) {
            assertTrue(noteMngr.getNoteTitles().contains(dummyTitle),
                    "Note stack should contain each added note.");
        }
    }
    
    @Test
    public void addOneNewNoteWIthoutTitleTest() {
        final NoteManager noteMngr = TestHelper.newNoteManager();
        
        assertEquals(0, noteMngr.getNoteCount(), "Note stack should be empty.");
        
        final String newNoteTitle = noteMngr.addNewNote();
        
        assertEquals(NoteManager.DEFAULT_NOTE_TITLE, newNoteTitle,
                "First untitled note should have dafault title.");
        assertEquals(newNoteTitle, getOnlyElement(noteMngr.getNotes()).getTitle(),
                "Note in stack should have returned title.");
    }
    
    @Test
    public void addNewNotesWithoutTitlesTest() {
        final NoteManager noteMngr = TestHelper.newNoteManager();
        // Fill a list of dummy note titles.
        final int noteCount = 10;
        
        List<String> titles = Lists.newArrayList();
        for (int i = 0; i < noteCount; i++) {
            String title = noteMngr.addNewNote();
            
            assertFalse(titles.contains(title), "New title should be unique.");
            
            titles.add(title);
        }
        assertEquals(noteCount, Sets.newHashSet(titles).size(), 
                "Should have expected number of unique titles."); 
        
        assertEquals(noteMngr.getNoteCount(), 
                Sets.newHashSet(noteMngr.getNoteTitles()).size(), 
                "All notes should have unique titles.");
        
        // Save the added notes; this will invoke validation what will ensure 
        // that all the notes have unique titles.
        noteMngr.saveAll();
    }
    
    @Test(expectedExceptions = DataStoreException.class)
    public void saveNotesWithDuplicateTitlesTest() {
        final NoteManager noteMngr = TestHelper.newNoteManager();
        
        final String title = "foobar";
        noteMngr.addNewNote(title);
        
        assertEquals(1, noteMngr.getNotes().size(), 
                "There should be only one note in the stack.");
        assertEquals(title, getOnlyElement(noteMngr.getNotes()).getTitle(),
                "The only note in the stack should be the added note.");
        
        noteMngr.addNewNote(title);
        noteMngr.saveAll();
    }
}
