/**
 * Junk - EnumSelectAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.enumselect;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Action;

import org.apache.commons.lang.StringUtils;

import dmh.swing.AbstractActionObserver;

/**
 * Action object representing the selection of an enum value.
 *
 * @author davehuffman
 */
public final class EnumSelectAction<T extends Enum<T>> extends AbstractActionObserver {
    private static final long serialVersionUID = 1L;
    
    private final T enumValue;
    private final EnumSelectable<T> enumSelectable;
    
    /**
     * Construct a new enum select action.
     * @param enumValue The value this action represents.
     * @param enumSelectable Handler object for enum selections.
     */
    EnumSelectAction(T enumValue, EnumSelectable<T> enumSelectable) {
        super(StringUtils.capitalize(enumValue.toString().toLowerCase()));
        this.enumValue = enumValue;
        this.enumSelectable = enumSelectable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enumSelectable.onEnumSelect(enumValue, e);
    }

    @Override
    public void update(Observable o, Object arg) {
        putValue(Action.SELECTED_KEY, (enumValue == arg));
    }
    
    T getEnumValue() {
        return enumValue;
    }
}
