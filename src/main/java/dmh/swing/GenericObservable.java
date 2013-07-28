/**
 * Kuebiko - GenericObservable.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing;

import java.util.Observable;

import javax.swing.SwingUtilities;

/**
 * An {@link java.util.Observable} class to allow other classes to implement
 * observable behavior via composition, rather than inheritance.
 *
 * @author davehuffman
 */
public class GenericObservable extends Observable {
    public void setChangedAndNotify() {
        setChangedAndNotify(null);
    }

    public void setChangedAndNotify(final Object arg) {
        setChanged();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                notifyObservers(arg);
            }});
    }
}
