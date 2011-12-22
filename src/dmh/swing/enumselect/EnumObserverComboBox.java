/**
 * Junk - ObserverComboBox.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.enumselect;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JComboBox;

/**
 * Combobox UI component capable of listening for enum selections from the 
 * outside world. 
 *
 * @author davehuffman
 */
public class EnumObserverComboBox<T extends Enum<T>> 
extends JComboBox implements Observer {
    private static final long serialVersionUID = 1L;

    /**
     * Construct a combobox item component.
     * @param enumClass Enumeration class represented by the combobox.
     * @param action Action to be performed when the user makes a selection.
     */
    public EnumObserverComboBox(Class<T> enumClass, Action action) {
        super(enumClass.getEnumConstants());
        setAction(action);
    }

    @Override
    public void update(Observable o, Object arg) {
        setSelectedItem(arg);
    }
}
