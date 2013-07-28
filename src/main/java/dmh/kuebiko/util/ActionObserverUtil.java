/**
 * Kuebiko - ActionObserverUtil.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.util;

import java.util.Observable;

import dmh.swing.AbstractActionObserver;

/**
 * Utility for working with action observer objects.
 *
 * @author davehuffman
 */
public final class ActionObserverUtil {
    private ActionObserverUtil() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
   public static void registerEnMass(ActionManager actionMngr, Observable observable, 
            AbstractActionObserver... actions) {
        for (AbstractActionObserver action: actions) {
            actionMngr.addAction(action);
            observable.addObserver(action);
        }
    }
}
