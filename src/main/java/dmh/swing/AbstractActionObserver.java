/**
 * Kuebiko - AbstractActionObserver.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.swing;

import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * Superclass for UI actions that may also observe events.
 *
 * @author davehuffman
 */
public abstract class AbstractActionObserver extends AbstractAction 
implements Observer {
    private static final long serialVersionUID = 1L;

    public AbstractActionObserver() {}

    public AbstractActionObserver(String name, Icon icon) {
        super(name, icon);
    }

    public AbstractActionObserver(String name) {
        super(name);
    }
}
