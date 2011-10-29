/**
 * Kuebiko - SaveStackAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * Swing action for saving a stack of notes.
 *
 * @author davehuffman
 */
public class SaveStackAction extends UnimplementedAction {
    private static final long serialVersionUID = 1L;
    
    private final NoteFrame noteFrame;
    
    public SaveStackAction(NoteFrame noteFrame) {
        super("Save Stack"); // TODO i18n.
        this.noteFrame = noteFrame;
        
        putValue(SHORT_DESCRIPTION, "Save a stack of notes."); // TODO i18n.
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_S, 
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Synchronize the current note before saving.
        noteFrame.getNotePanel().syncNote();
        noteFrame.getNoteMngr().saveAll();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        // Do noting.
    }
}
