/**
 * kuebiko - InsertHeadingAction.java
 * Copyright 2012 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.huxley.action;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.StringUtils;

import dmh.swing.huxley.constant.TextAction;

/**
 * Swing action for inserting header text into a text control.
 *
 * @author davehuffman
 */
public class InsertHeadingAction extends AbstractPlainTextAction {
    private static final long serialVersionUID = 1L;

    private final String token;

    public InsertHeadingAction(TextAction textAction, String token,
                JTextComponent textComponent) {
        super(textAction, textComponent);
        this.token = token;
    }

    @Override
    public int manipulateText(Document document, int start, int end) {
        try {
            final int offset = start;
            final int length = end - start;

            // Extract the selected text.
            String title = StringUtils.trimToEmpty(document.getText(offset, length));
            document.remove(offset, length);

            // Add the header.
            String bar = StringUtils.repeat(token, 40);
            String insertText = "\n " + title + "\n" + bar + "\n";
            document.insertString(offset, insertText, null);

            // Return the caret position.
            return start + ("".equals(title) ? 2 : insertText.length());
        } catch (BadLocationException e) {
            // This indicates a programming error.
            throw new RuntimeException(e);
        }
    }
}
