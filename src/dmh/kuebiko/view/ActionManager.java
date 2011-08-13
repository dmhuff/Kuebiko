/**
 * Kuebiko - ActionManager.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import java.util.Map;

import javax.swing.Action;

import com.google.common.collect.Maps;

/**
 * Manager class for Swing actions used in the UI.
 *
 * @author davehuffman
 */
class ActionManager {
    /** Lookup table for actions. */
    final Map<Class<? extends Action>, Action> actionMap = Maps.newHashMap();
    
    /**
     * Retrieve an instance of an action by its class.
     * @param clazz The class of the desired action.
     * @return An instance of an action.
     */
    Action getAction(Class<? extends Action> clazz) {
        Action action = actionMap.get(clazz);
        // If necessary, lazily instantiate an instance of the requested action.
        if (action == null) {
            try {
                action = clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            putAction(action);
        }
        return action;
    }

    /**
     * XXX is this method necessary (or even a good idea?)
     * Manually add an action to the set of managed actions.
     * @param action The action to add.
     */
    void putAction(Action action) {
        actionMap.put(action.getClass(), action);
    }
}
