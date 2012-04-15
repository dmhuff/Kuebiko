/**
 * Kuebiko - ActionObserverUtil.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
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
