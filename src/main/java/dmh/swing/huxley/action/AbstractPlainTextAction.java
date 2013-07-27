/**
 * Junk - AbstractHtmlStyleAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley.action;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import dmh.swing.AbstractActionObserver;
import dmh.swing.huxley.HuxleyImageManager;
import dmh.swing.huxley.constant.TextAction;

/**
 * Base class for Swing actions that manipulate text.
 *
 * @author davehuffman
 */
public abstract class AbstractPlainTextAction extends AbstractActionObserver {
    private static final long serialVersionUID = 1L;

    public final TextAction actionId;
    protected final JTextComponent textComponent;

    public AbstractPlainTextAction(TextAction actionId, JTextComponent textComponent) {
        super(actionId.actionName,
                new ImageIcon(HuxleyImageManager.get().getImage(actionId.actionName)));
        this.actionId = actionId;
        this.textComponent = textComponent;
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        final Document document = textComponent.getDocument();

        final int start = textComponent.getSelectionStart();
        final int end = textComponent.getSelectionEnd();

        int cursor = manipulateText(document, start, end);

        textComponent.setCaretPosition(cursor);
    }

    public abstract int manipulateText(Document document, int start, int end);

    @Override
    public void update(Observable o, Object arg) { /* By default do nothing. */ }

    public TextAction getActionId() {
        return actionId;
    }
}
