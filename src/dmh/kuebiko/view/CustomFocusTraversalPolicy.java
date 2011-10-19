/**
 * Kuebiko - CustomTraversalPolicy.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * 
 * Derived from Sun's Focus Traversal Demo:
 * http://download.oracle.com/javase/tutorial/uiswing/examples/misc/FocusTraversalDemoProject/src/misc/FocusTraversalDemo.java
 * 
 * TODO license info.
 */
package dmh.kuebiko.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import dmh.kuebiko.Main;

/**
 * TODO there's nothing Kuebiko specific here. consider moving to a util package.
 * Custom focus traversal policy; allows for the traversal of a set of 
 * components in order.
 * 
 * @author davehuffman
 */
public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
    private final List<Component> components;

    public CustomFocusTraversalPolicy(Component... components) {
        this(Arrays.asList(components));
    }
    
    public CustomFocusTraversalPolicy(List<Component> order) {
        Preconditions.checkNotNull(order);
        Preconditions.checkArgument(!order.isEmpty());
        
        this.components = ImmutableList.copyOf(order);
    }

    @Override
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
//        Main.log("getComponentAfter([%s])", aComponent.getClass());
        int idx = (components.indexOf(aComponent) + 1) % components.size();
        return components.get(idx);
    }

    @Override
    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
//        Main.log("getComponentBefore([%s])", aComponent.getClass());
        int idx = components.indexOf(aComponent) - 1;
        if (idx < 0) {
            idx = components.size() - 1;
        }
        return components.get(idx);
    }

    @Override
    public Component getDefaultComponent(Container focusCycleRoot) {
        return components.get(0);
    }

    @Override
    public Component getLastComponent(Container focusCycleRoot) {
        return Iterables.getLast(components);
    }

    @Override
    public Component getFirstComponent(Container focusCycleRoot) {
        return components.get(0);
    }
}
