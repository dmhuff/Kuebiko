/**
 * Junk - ParagraphStyleAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.huxley;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import dmh.swing.html.constants.ParagraphType;

/**
 * Action for changing the paragraph style of a block of text.
 *
 * @author davehuffman
 */
public class ParagraphStyleAction extends AbstractHtmlStyleAction {
    private static final long serialVersionUID = 1L;

    public final static String ACTION_NAME = "huxley-paragraph";

    private final JTextComponent textComponent;

    public ParagraphStyleAction(JTextComponent textComponent) {
        super(ACTION_NAME);
        this.textComponent = textComponent;

        textComponent.getActionMap().put(getValue(NAME), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        final ParagraphType paragraphStyle;
        if (source instanceof JComboBox) {
            paragraphStyle = (ParagraphType) ((JComboBox) source).getSelectedItem();
        } else {
            throw new IllegalStateException(
                    String.format("Unsupported source class [%s].", source.getClass()));
        }

        if (paragraphStyle != null) {
            performStyleChange(textComponent,  paragraphStyle.tag);
        }
    }

    @Override
    public void update(Observable o, Object arg) {/* Do nothing. */}
}
