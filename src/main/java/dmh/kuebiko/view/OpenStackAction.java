/**
 * Kuebiko - OpenStackAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import dmh.swing.UnimplementedAction;

/**
 * Swing action for opening a note stack.
 *
 * @author davehuffman
 */
public class OpenStackAction extends UnimplementedAction {
    private static final long serialVersionUID = 1L;

    public OpenStackAction() {
        super("Open Stack");

        putValue(SHORT_DESCRIPTION, "Open a stack of notes.");
        putValue(LONG_DESCRIPTION, getValue(SHORT_DESCRIPTION));
        putValue(ACCELERATOR_KEY, getKeyStroke(KeyEvent.VK_O,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Do nothing.
    }
}
