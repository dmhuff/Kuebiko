/**
 * Kuebiko - OpenStackAction.java
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
