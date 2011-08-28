/**
 * Kuebiko - RenameNoteAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * Swing action for renaming the current note.
 *
 * @author davehuffman
 */
public class RenameNoteAction extends UnimplementedAction {
    private static final long serialVersionUID = 1L;
    
    private final NoteFrame noteFrame;
    
    public RenameNoteAction(NoteFrame noteFrame) {
        super("Rename Note"); // TODO i18n.
        this.noteFrame = noteFrame;
        
        putValue(SHORT_DESCRIPTION, "Rename a note."); // TODO i18n.
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_R, InputEvent.META_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_R);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
    }

    @Override
    public void update(Observable o, Object arg) {
        setEnabled(noteFrame.isNoteSelected());
    }
}
