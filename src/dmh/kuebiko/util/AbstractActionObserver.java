/**
 * Kuebiko - AbstractActionObserver.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.util;

import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public abstract class AbstractActionObserver extends AbstractAction 
implements Observer {
    private static final long serialVersionUID = 1L;

    public AbstractActionObserver() {
        super();
    }

    public AbstractActionObserver(String name, Icon icon) {
        super(name, icon);
    }

    public AbstractActionObserver(String name) {
        super(name);
    }
}
