/**
 * Kuebiko - AbstractActionObserver.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
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
