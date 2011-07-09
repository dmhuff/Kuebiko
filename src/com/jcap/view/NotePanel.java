/**
 * Kuebiko - NotePanel.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledEditorKit;

import org.jdesktop.swingx.JXEditorPane;
import org.jdesktop.swingx.action.ActionManager;

import com.jcap.Main;
import com.jcap.model.Note;

/**
 * UI panel for displaying and editing notes.
 *
 * @author davehuffman
 */
class NotePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private enum CardId { NO_NOTE_MESSAGE, NOTE_TEXT }
    
    private final CardLayout cardLayout;
    //final JTextArea noteTextArea;
    final JXEditorPane noteTextArea;
    
    /** The currently selected note. */
    private Note note;
    
    private boolean noteChanged;

    NotePanel() {
        this.addFocusListener(
                new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // When the panel gains focus, transfer focus to the
                        // note text area if it is visible.
                        if (noteTextArea.isVisible()) {
                            noteTextArea.requestFocusInWindow();
                        }
                    }
                });
        
        
        // Panel Layout.
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        
        // No Note Label.
        JLabel noSelectionLabel = new JLabel("No note selected"); // TODO i18n.
        noSelectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(noSelectionLabel, CardId.NO_NOTE_MESSAGE.toString());
        
        JPanel panel = new JPanel();
        add(panel, CardId.NOTE_TEXT.toString());
        panel.setLayout(new BorderLayout(0, 0));
        
        // Note Text Area.
//        noteTextArea = new JTextArea();
//        noteTextArea.setLineWrap(true);
//        noteTextArea.setTabSize(4);
        noteTextArea = new JXEditorPane("text/html", "");
        
        noteTextArea.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        noteChanged = true;
                    }
        
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        noteChanged = true;
                    }
        
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        noteChanged = true;
                    }
                });
        
        noteTextArea.addFocusListener(
                new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        Main.log("noteTextArea focus gained.");
                    }
                    @Override
                    public void focusLost(FocusEvent e) {
                        Main.log("noteTextArea focus lost.");
                    }
                });
        
        // Note Text Scroll Pane.
        JScrollPane noteTextScrollPane = new JScrollPane();
        panel.add(noteTextScrollPane, BorderLayout.CENTER);
        noteTextScrollPane.setBorder(null);
        noteTextScrollPane.setViewportView(noteTextArea);
        
        
//        JToolBar toolBar = new ActionContainerFactory(noteTextArea.getActionMap()).createToolBar(noteTextArea.getCommands());
//        //Application.getInstance().getContext().getActionMap(this);
        
        
        ActionManager.getInstance().put(HTMLEditorKit.BOLD_ACTION, HTMLEditorKit)
        
        List<Object> foobar = Arrays.asList(ActionManager.getInstance().keys());
//        List<Object> foobar = Arrays.asList(noteTextArea.getActionMap().allKeys());
        Collections.sort(foobar, new Comparator<Object>(){
            @Override
            public int compare(Object o1, Object o2) {
                return String.valueOf(o1).compareTo(String.valueOf(o2));
            }});
        
        for (Object foo: foobar) {
            System.out.println(foo);
        }
        
//        Main.log("bold=[%s].", ActionManager.getInstance().keys());
        
//        ActionManager manager = ActionManager.getInstance().get(HTMLEditorKit.BOLD_ACTION);
        
        JToolBar toolBar = new JToolBar();
//        toolBar.add(map.get("cut"));
//        toolBar.add(map.get("copy"));
//        toolBar.add(map.get("paste"));
        toolBar.addSeparator();
        //TODO doesn't work this way because the action is uninstalled and reinstalled every call
//        toolbar.add(editor.getActionMap().get("undo"));
//        toolbar.add(editor.getActionMap().get("redo"));
        toolBar.addSeparator();
//        toolBar.add(new JToggleButton(map.get("bold")));
        toolBar.add(new JToggleButton(new StyledEditorKit.ItalicAction()));
//        toolBar.add(new JToggleButton(map.get("underline")));
        
        toolBar.addSeparator();
        toolBar.add(noteTextArea.getParagraphSelector());
        
        panel.add(toolBar, BorderLayout.NORTH);
        
        // By default, no note should be displayed.
        setNote(null);
    }

    /**
     * Handler for a note selection.
     * @param prevNote The previously displayed note.
     */
    private void onNoteSelected(Note prevNote) {
        Main.log("onNoteSelected(); [%s]", note);
        
        // Save any changes made to the previously selected note.
        if (prevNote != null && noteChanged) {
            prevNote.setText(noteTextArea.getText());
        }
        
        noteChanged = false;
        
        if (note == null) {
            cardLayout.show(this, CardId.NO_NOTE_MESSAGE.toString());
            return;
        }
        noteTextArea.setText(note.getText());
        cardLayout.show(this, CardId.NOTE_TEXT.toString());
    }
    
    /**
     * @return The note currently displayed in the panel, or null if no note is
     *         being displayed.
     */
    public Note getNote() {
        return note;
    }

    /**
     * Assign a note to be displayed in the panel.
     * @param note The note to be displayed, or null if no note is to be 
     *             displayed.
     */
    public void setNote(Note note) {
        final Note prevNote = this.note;
        this.note = note;
        onNoteSelected(prevNote);
    }

//    /**
//     * @return The UI component for displaying a note's text.
//     */
//    public JTextArea getNoteTextArea() {
//        return noteTextArea;
//    }
}
