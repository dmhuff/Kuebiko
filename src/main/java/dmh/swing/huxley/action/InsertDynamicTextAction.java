/**
 * kuebiko - InsertHeadingAction.java
 * Copyright 2012 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley.action;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.google.common.base.Function;

import dmh.swing.huxley.constant.TextAction;

/**
 * Swing action for inserting dynamically generated text into a text control.
 *
 * @author davehuffman
 */
public class InsertDynamicTextAction extends AbstractPlainTextAction {
    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(InsertDynamicTextAction.class);

    private final Function<Void, String> textGenerator;

    public InsertDynamicTextAction(TextAction textAction,
                Function<Void, String> textGenerator,
                JTextComponent textComponent) {
        super(textAction, textComponent);
        this.textGenerator = textGenerator;
    }

    @Override
    public int manipulateText(Document document, int start, int end) {
        log.debug("manipulateText.");

        try {
            final int offset = start;
            final int length = end - start;

            // Extract the selected text.
            document.remove(offset, length);

            // Re-insert the text, wrapped in tokens.
            String insertText = textGenerator.apply(null);
            document.insertString(offset, insertText, null);

            // Return the caret position.
            return start + insertText.length();
        } catch (BadLocationException e) {
            // This indicates a programming error.
            throw new RuntimeException(e);
        }
    }
}
