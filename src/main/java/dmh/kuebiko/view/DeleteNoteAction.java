/**
 * Kuebiko - DeleteNoteAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import dmh.swing.AbstractActionObserver;

/**
 * Swing action for deleting the currently selected note.
 *
 * @author davehuffman
 */
public class DeleteNoteAction extends AbstractActionObserver {
private static final long serialVersionUID = 1L;

    private final NoteStackFrame noteFrame;

    public DeleteNoteAction(NoteStackFrame noteFrame) {
        super("Delete Note");
        this.noteFrame = noteFrame;

        putValue(SHORT_DESCRIPTION, "Delete the selected note from the stack.");
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_D,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_DOWN_MASK));
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        noteFrame.deleteSelectedNote();
    }

    @Override
    public void update(Observable o, Object arg) {
        setEnabled(noteFrame.isNoteSelected());
    }
}
