/**
 * Kuebiko - HuxleyUiManager.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import dmh.kuebiko.Main;
import dmh.kuebiko.Main.Setting;
import dmh.swing.AbstractActionObserver;
import dmh.swing.huxley.action.AbstractPlainTextAction;
import dmh.swing.huxley.action.InsertDynamicTextAction;
import dmh.swing.huxley.action.InsertHeadingAction;
import dmh.swing.huxley.action.WrapTextAction;
import dmh.swing.huxley.constant.TextAction;
import dmh.util.Callback;

/**
 * Manager object for the Huxley UI.
 *
 * @author davehuffman
 */
public class HuxleyUiManager {
    private final JPanel uiPanel = new JPanel();
    private final JTextArea textArea;
    
    private final EnumMap<TextAction, AbstractPlainTextAction> textActions = Maps.newEnumMap(TextAction.class);
    
    private final DocumentListener textChangeListener;
    private boolean textChanged = false;
    private Callback<Boolean> onTextChangeCallback = null;

    /**
     * Construct a Huxley UI manager with a basic Swing text area component.
     */
    public HuxleyUiManager() {
        this(new JTextArea());
        
    }
    /**
     * Construct a Huxley UI manager with a custom text area component.
     */
    public HuxleyUiManager(JTextArea ta) {
        this.textArea = ta;
        
        textActions.put(TextAction.HEADER_1, 
                new InsertHeadingAction(TextAction.HEADER_1, "#", textArea));
        textActions.put(TextAction.HEADER_2, 
                new InsertHeadingAction(TextAction.HEADER_2, "=", textArea));
        textActions.put(TextAction.HEADER_3, 
                new InsertHeadingAction(TextAction.HEADER_3, "-", textArea));
        textActions.put(TextAction.INSERT_LINK, 
                new WrapTextAction(TextAction.INSERT_LINK, "[", "]", textArea));
        textActions.put(TextAction.INSERT_DATE, 
                new InsertDynamicTextAction(TextAction.INSERT_DATE, 
                        new Function<Void, String>() {
                            @Override
                            public String apply(Void input) {
                                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            }
                        }, textArea));
        
        uiPanel.setLayout(new BorderLayout());
        
        // Tool Bar.
        Box toolBarBox = Box.createHorizontalBox();
        toolBarBox.setAlignmentX(0);
        toolBarBox.add(ToolBarBuilder.build(this));
        uiPanel.add(toolBarBox, BorderLayout.NORTH);
        
        // Text Area.
        textArea.setFont(new Font(
                Main.getSetting(Setting.FONT_NAME), Font.PLAIN, 
                Integer.parseInt(Main.getSetting(Setting.FONT_SIZE))));
        
        JScrollPane textScrollPane = new JScrollPane();
        uiPanel.add(textScrollPane, BorderLayout.CENTER);
        textScrollPane.setBorder(null);
        textScrollPane.setViewportView(textArea);
        
        uiPanel.addFocusListener(
                new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // When the panel gains focus, transfer focus to the
                        // note text area if it is visible.
                        if (textArea.isVisible()) {
                            textArea.requestFocusInWindow();
                        }
                    }
                });
        
//        textArea.setDocument(textArea.getEditorKit().createDefaultDocument());
        textChangeListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                markTextAsChanged();
            }
      
            @Override
            public void removeUpdate(DocumentEvent e) {
                markTextAsChanged();
            }
      
            @Override
            public void changedUpdate(DocumentEvent e) {
                markTextAsChanged();
            }
        };
        textArea.getDocument().addDocumentListener(textChangeListener);
    }

    /** DEBUG for development testing. */
    public void describeSelection() {
        String selectedText = textArea.getSelectedText();
        if (selectedText != null) {
            selectedText = selectedText.replaceAll("\n", "\\\\n");
        }
        
        System.out.printf("%d-%d:[%s]%n", 
                textArea.getSelectionStart(),
                textArea.getSelectionEnd(),
                selectedText);
        
    }
    
    /**
     * @param action
     * @return
     */
    public AbstractActionObserver getTextAction(TextAction action) {
        return textActions.get(action);
    }
    
    public JPanel getUiPanel() {
        return uiPanel;
    }
    
    public String getText() {
        return textArea.getText();
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }
    
    /**
     * Reset the text contents to a pristine state.
     * @param text The text contents after the state has been reset. May be null.
     */
    public void resetText(String text) {
        // Set the text without the change listener to prevent stray change events.
        textArea.getDocument().removeDocumentListener(textChangeListener);
        textArea.setText(text);
        textArea.getDocument().addDocumentListener(textChangeListener);
        textArea.setCaretPosition(0);
        
        if (textArea instanceof RSyntaxTextArea) {
            ((RSyntaxTextArea) textArea).discardAllEdits();
        }
        
        resetTextChanged();
    }

    public void resetTextChanged() {
        textChanged = false;
    }
    
    public boolean isTextChanged() {
        return textChanged;
    }
    
//    private void setTextChanged(boolean textChanged) {
//        boolean prevValue = this.textChanged;
//        this.textChanged = textChanged;
//        if (prevValue != this.textChanged && onTextChangeCallback != null) {
//            onTextChangeCallback.callback(this.textChanged);
//        }
//    }
    
    private void markTextAsChanged() {
        if (!textChanged && onTextChangeCallback != null) {
            // Only call the callback the first time the text is changed.
            textChanged = true;
            onTextChangeCallback.callback(textChanged);
        }
    }
    
    public void setOnTextChangeCallback(Callback<Boolean> onTextChangeCallback) {
        this.onTextChangeCallback = onTextChangeCallback;
    }
}
