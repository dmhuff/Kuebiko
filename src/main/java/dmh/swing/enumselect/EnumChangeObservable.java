/**
 * Junk - EnumChangeObservable.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.enumselect;

import java.util.Observable;

/**
 * Observable object for enum value changes.
 *
 * @author davehuffman
 */
class EnumChangeObservable<T extends Enum<T>> extends Observable {
    private T currentValue;

    T getCurrentValue() {
        return currentValue;
    }

    void setCurrentValueAndNotifyObservers(T currentValue) {
        if (this.currentValue != currentValue) {
            setChanged();
        }
        this.currentValue = currentValue;
        
        notifyObservers(currentValue);
    }
}
