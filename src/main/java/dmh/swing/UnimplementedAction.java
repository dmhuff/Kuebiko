/**
 * Kuebiko - UnimplementedAction.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Superclass for actions that have not yet been implemented.
 *
 * @author davehuffman
 */
public class UnimplementedAction extends AbstractActionObserver {
    private static final long serialVersionUID = 1L;

    public UnimplementedAction() {
        super();
    }

    public UnimplementedAction(String name, Icon icon) {
        super(name, icon);
    }

    public UnimplementedAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Not implemented!");
    }

    @Override
    public void update(Observable o, Object arg) {
    	JOptionPane.showMessageDialog(null, "Not implemented!");
    }
}
