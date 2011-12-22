/**
 * Junk - ObserverCheckBoxMenuItem.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.enumselect;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;

/**
 * Menu item UI component capable of listening for enum selections from the 
 * outside world. 
 *
 * @author davehuffman
 */
class EnumObserverCheckBoxMenuItem<T extends Enum<T>> 
extends JCheckBoxMenuItem implements Observer {
    private static final long serialVersionUID = 1L;
    
    private final Enum<T> enumValue;

    /**
     * Construct a new menu item component.
     * @param enumValue Enumeration value represented by the menu item.
     * @param action Action to be performed when the user makes a selection.
     */
    EnumObserverCheckBoxMenuItem(Enum<T> enumValue, Action action) {
        super(action);
        this.enumValue = enumValue;
    }

    @Override
    public void update(Observable o, Object arg) {
        setSelected(enumValue == arg);
    }
}
