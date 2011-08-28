/**
 * Kuebiko - NewNoteAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import dmh.kuebiko.util.AbstractActionObserver;

/**
 * Swing action for creating a new note in the current stack.
 *
 * @author davehuffman
 */
class NewNoteAction extends AbstractActionObserver {
    private static final long serialVersionUID = 1L;
    
    private final NoteFrame noteFrame;
    
    NewNoteAction(NoteFrame noteFrame) {
        super("New Note"); // TODO i18n.
        this.noteFrame = noteFrame;
        
        putValue(SHORT_DESCRIPTION, "Add a new note to the stack."); // TODO i18n.
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final NoteTable noteTable = noteFrame.getNoteTable();

        // Determine note title.
        String noteTitle = noteFrame.getSearchText().getText();
        if (isBlank(noteTitle) || noteFrame.isInEditMode()) {
            noteTitle = noteTable.getNoteTableModel().addNewNote();
        } else {
            noteTable.getNoteTableModel().addNewNote(noteTitle);
        }
        noteFrame.getSearchText().setText(noteTitle);
        
        if (noteFrame.isInEditMode()) {
            // If we're in edit mode, we need to do a bit more work on the UI, 
            // since most of the needed setup work happens only in search mode.
            noteTable.filter(noteFrame.getSearchText().getText());
            noteTable.selectNote(noteTitle);
        }
        // Move focus to the note panel to allow the user to immediately start
        // editing the text of the new note.
        noteFrame.getNotePanel().requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        // Do nothing for now; there's no need for this action to handle updates.
    }
}
