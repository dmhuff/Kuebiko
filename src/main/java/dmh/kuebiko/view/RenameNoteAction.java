/**
 * Kuebiko - RenameNoteAction.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import dmh.swing.UnimplementedAction;

/**
 * Swing action for renaming the current note.
 *
 * @author davehuffman
 */
public class RenameNoteAction extends UnimplementedAction {
    private static final long serialVersionUID = 1L;

    private final NoteStackFrame noteFrame;

    public RenameNoteAction(NoteStackFrame noteFrame) {
        super("Rename Note");
        this.noteFrame = noteFrame;

        putValue(SHORT_DESCRIPTION, "Rename a note.");
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_R,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
