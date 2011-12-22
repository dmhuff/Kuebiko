/**
 * Junk - MasterEnumSelectAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.enumselect;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumMap;
import java.util.List;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Master action governing the selection actions for the entire set of values
 * defined within a given enumeration.
 *
 * @author davehuffman
 */
public class MasterEnumSelectAction<T extends Enum<T>> extends AbstractAction {
    private static final long serialVersionUID = 1L;
    
    /** Action key for for the selected enum value. */
    public final static String SELECTED_VALUE_KEY = "huxley-sel-val";

    private final Class<T> enumClass;
    private final EnumSelectable<T> enumSelectable;
    
    private final EnumMap<T, Action> actions;
    private final EnumChangeObservable<T> enumChangeObservable = new EnumChangeObservable<T>();

    /**
     * Construct a new master enum selection action.
     * @param enumClass The enum class defining all possible selections.
     * @param sourceAction An action defining selection behavior.
     */
    public MasterEnumSelectAction(Class<T> enumClass, final Action sourceAction) {
        this(enumClass, new EnumSelectable<T>() {
            @Override
            public void onEnumSelect(T enumValue, ActionEvent e) {
                sourceAction.actionPerformed(e);
            }
        });
    }
    
    /**
     * Construct a new master enum selection action.
     * @param enumClass The enum class defining all possible selections.
     * @param enumSelectable Handler for enum selection events.
     */
    public MasterEnumSelectAction(Class<T> enumClass, EnumSelectable<T> enumSelectable) {
        this.enumClass = enumClass;
        this.enumSelectable = enumSelectable;
        
        actions = Maps.newEnumMap(enumClass);
        for (T enumValue: enumClass.getEnumConstants()) {
            EnumSelectAction<T> action = newEnumSelectAction(enumValue);
            actions.put(enumValue, action);
            addObserver(action);
        }
        
        addPropertyChangeListener(
                new PropertyChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (SELECTED_VALUE_KEY.equals(evt.getPropertyName())) {
                            enumChangeObservable.setCurrentValueAndNotifyObservers(
                                    (T) evt.getNewValue());
                        }
                    }
                });
        
        putValue(SELECTED_VALUE_KEY, null);
    }
    
    /**
     * Factory method for actions for the selection of an individual enum value.
     * @param actionEnumValue The enum value that corresponds to the action. 
     * @return A new enum select action.
     */
    private EnumSelectAction<T> newEnumSelectAction(final T actionEnumValue) {
        return new EnumSelectAction<T>(actionEnumValue, 
                new EnumSelectable<T>() {
                    @Override
                    public void onEnumSelect(T enumValue, ActionEvent e) {
                        enumSelectable.onEnumSelect(enumValue, e);
                        setSelectedValue(enumValue);
                    }
                });
    }
    
    public List<JMenuItem> buildMenuItems() {
        List<JMenuItem> menuItems = Lists.newArrayListWithCapacity(
                enumClass.getEnumConstants().length);
        for (T enumValue: enumClass.getEnumConstants()) {
            EnumObserverCheckBoxMenuItem<T> menuItem = 
                    new EnumObserverCheckBoxMenuItem<T>(enumValue, getAction(enumValue));
            enumChangeObservable.addObserver(menuItem);
            menuItems.add(menuItem);
        }
        return menuItems;
    }
    
    public JComboBox buildComboBox() {
        final EnumObserverComboBox<T> comboBox = new EnumObserverComboBox<T>(enumClass, this);
        this.addObserver(comboBox);
        return comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JComboBox) {
            JComboBox sourceComboBox = (JComboBox) source;
            Object selectedItem = sourceComboBox.getSelectedItem();
            Action action = getAction(selectedItem);

            action.putValue(Action.SELECTED_KEY, true);
            action.actionPerformed(e);
        }
    }

    public void addObserver(Observer observer) {
        enumChangeObservable.addObserver(observer);
    }
    
    public Object getselectedValue() {
        return getValue(SELECTED_VALUE_KEY);
    }
    
    public void setSelectedValue(T selectedValue) {
        putValue(SELECTED_VALUE_KEY, selectedValue);
    }
    
    public Action getAction(T value) {
        return actions.get(value);
    }

    @SuppressWarnings("unchecked")
    private Action getAction(Object value) {
        return getAction((T) value);
    }
}
