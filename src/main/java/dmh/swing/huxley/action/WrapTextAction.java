/**
 * kuebiko - InsertHeadingAction.java
 * Copyright 2012 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley.action;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.StringUtils;

import dmh.swing.huxley.constant.TextAction;

/**
 * TODO Document.
 *
 * @author davehuffman
 */
public class WrapTextAction extends AbstractPlainTextAction {
    private static final long serialVersionUID = 1L;
    
    private final String prefixToken;
    private final String suffixToken;
    
    public WrapTextAction(TextAction textAction, 
                String prefixToken, String suffixToken,
                JTextComponent textComponent) {
        super(textAction, textComponent);
        this.prefixToken = prefixToken;
        this.suffixToken = suffixToken;
    }
    
    @Override
    public int manipulateText(Document document, int start, int end) {
        try {
            final int offset = start;
            final int length = end - start;
            
            // Extract the selected text.
            String selectedText = StringUtils.trimToEmpty(document.getText(offset, length));
            document.remove(offset, length);
            
            // Re-insert the text, wrapped in tokens.
            String insertText = prefixToken + selectedText + suffixToken;
            document.insertString(offset, insertText, null);
            
            // Return the caret position.
            return start + ("".equals(selectedText) ? 1 : insertText.length());
        } catch (BadLocationException e) {
            // This indicates a programming error.
            throw new RuntimeException(e);
        }
    }
}
