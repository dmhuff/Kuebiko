/**
 * Kuebiko - ActionManager.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */

package dmh.kuebiko.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.swing.Action;

import com.google.common.collect.Maps;

/**
 * Manager class for Swing actions used in the UI.
 *
 * @author davehuffman
 */
public final class ActionManager {
    /** Lookup table for actions. */
    final Map<Class<? extends Action>, Action> actionMap = Maps.newHashMap();
    
    /**
     * Retrieve an instance of an action by its class.
     * @param clazz The class of the desired action.
     * @return An instance of an action.
     */
    public Action getAction(Class<? extends Action> clazz) {
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
            addAction(action);
        }
        return action;
    }
    
    /**
     * @return An immutable collection of all actions within this manager's domain.
     */
    public Collection<Action> getActions() {
        return Collections.unmodifiableCollection(actionMap.values());
    }

    /**
     * Manually add an action to the set of managed actions.
     * @param action The action to add.
     */
    public void addAction(Action action) {
        actionMap.put(action.getClass(), action);
    }
    
    /**
     * Manually add at least one action to the set of managed actions.
     * @param first The first action to add.
     * @param actions All other actions to add.
     */
    public void addActions(Action first, Action... actions) {
        addAction(first);
        for (Action action: actions) {
            addAction(action);
        }
    }
}
