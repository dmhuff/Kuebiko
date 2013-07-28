/**
 * Junk - EnumSelectable.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.enumselect;

import java.awt.event.ActionEvent;

/**
 * Interface for objects that allow for the selection of a enum value.
 *
 * @author davehuffman
 */
public interface EnumSelectable<T extends Enum<T>> {
    /**
     * Handle the selection of a new enum value.
     * @param enumValue The value selected.
     * @param e The event triggered by the selection.
     */
    public void onEnumSelect(T enumValue, ActionEvent e);
}
