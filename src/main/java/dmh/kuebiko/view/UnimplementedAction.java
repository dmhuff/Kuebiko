/**
 * Kuebiko - UnimplementedAction.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import dmh.swing.AbstractActionObserver;

/**
 * Superclass for actions that have not yet been implemented.
 * <b><i>NOTE:</b></i> This class is temporary; once all the classes that 
 * subclass it have been implemented, it can be removed.
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
        // TODO Auto-generated method stub
        
    }
}
