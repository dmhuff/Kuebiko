/**
 * Kuebiko - OpenNoteAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import dmh.swing.AbstractActionObserver;

/**
 * Swing action for opening a note in the current stack.
 *
 * @author davehuffman
 */
class OpenNoteAction extends AbstractActionObserver {
    private static final long serialVersionUID = 1L;

    private final NoteStackFrame noteFrame;

    OpenNoteAction(NoteStackFrame noteFrame) {
        super("Open Note");
        this.noteFrame = noteFrame;

        putValue(SHORT_DESCRIPTION, "Open a note in the stack.");
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_L,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(MNEMONIC_KEY, KeyEvent.VK_L);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        noteFrame.getSearchText().requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        // Do nothing for now; there's no need for this action to handle updates.
    }
}
