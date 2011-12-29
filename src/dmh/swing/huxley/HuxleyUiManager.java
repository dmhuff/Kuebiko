/**
 * Junk - foobar.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import dmh.swing.html.SwingHtmlUtil;
import dmh.swing.html.constants.ParagraphType;
import dmh.swing.huxley.constants.TextAction;
import dmh.util.Callback;

/**
 * Manager object for the Huxley UI.
 *
 * @author davehuffman
 */
public class HuxleyUiManager {
    private final JPanel uiPanel = new JPanel();
    private final JEditorPane textArea = new JEditorPane("text/html", "");
    
    private boolean textChanged = false;
    private Callback<Boolean> onTextChangeCallback = null;

    public HuxleyUiManager() {
        buildUi();
    }

    /**
     * Build the Huxley UI panel.
     */
    private void buildUi() {
        uiPanel.setLayout(new BorderLayout());
        
        // Tool Bar.
        Box toolBarBox = Box.createHorizontalBox();
        toolBarBox.setAlignmentX(0);
        toolBarBox.add(ToolBarBuilder.build(textArea));
        uiPanel.add(toolBarBox, BorderLayout.NORTH);
        
        // Text Area.
        JScrollPane textScrollPane = new JScrollPane();
        uiPanel.add(textScrollPane, BorderLayout.CENTER);
        textScrollPane.setBorder(null);
        textScrollPane.setViewportView(textArea);
        
        // DEBUG remove.
        textArea.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("!!! prop change" + evt.getPropertyName());
            }
        });
        
        textArea.addCaretListener(new TextStyleCaretListener());
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
        
        textArea.setDocument(textArea.getEditorKit().createDefaultDocument());
        textArea.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        setTextChanged(true);
                    }
        
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        setTextChanged(true);
                    }
        
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        setTextChanged(true);
                    }
                });
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
    
    public JPanel getUiPanel() {
        return uiPanel;
    }
    
//    public JEditorPane getTextArea() {
//        return textArea;
//    }
    
    public String getText() {
        return textArea.getText();
    }
    
    /**
     * Reset the text contents to a pristine state.
     * @param text The text contents after the state has been reset.
     */
    public void resetText(String text) {
        textArea.setText(text);
        setTextChanged(false);
    }
    
    public boolean isTextChanged() {
        return textChanged;
    }
    
    private void setTextChanged(boolean textChanged) {
        boolean prevValue = this.textChanged;
        this.textChanged = textChanged;
        if (prevValue != this.textChanged && onTextChangeCallback != null) {
            onTextChangeCallback.callback(this.textChanged);
        }
    }
    
    public void setOnTextChangeCallback(Callback<Boolean> onTextChangeCallback) {
        this.onTextChangeCallback = onTextChangeCallback;
    }

    /**
     * Custom listener for caret updates; will toggle components for modifying 
     * text styles based on the text under the caret (a.k.a. cursor).
     * 
     * This class is based on the CaratHandler class in the SwingX JXEditorPane.
     * @see org.jdesktop.swingx.JXEditorPane.CaretHandler
     */
    private class TextStyleCaretListener implements CaretListener {
        @Override
        public void caretUpdate(final CaretEvent event) {
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
                
            StyledDocument document = (StyledDocument) textArea.getDocument();
            int dot = event.getDot();
//            dot = dot > 0 ? dot - 1 : dot;
            
            Element elem = document.getCharacterElement(dot);
            AttributeSet set = elem.getAttributes();

            final ActionMap actionMap = textArea.getActionMap();
            actionMap.get(TextAction.STYLE_BOLD.actionName).putValue(
                    Action.SELECTED_KEY, SwingHtmlUtil.isBold(elem));
            actionMap.get(TextAction.STYLE_ITALIC.actionName).putValue(
                    Action.SELECTED_KEY, SwingHtmlUtil.isItalic(elem));
            actionMap.get(TextAction.STYLE_UNDERLINE.actionName).putValue(
                    Action.SELECTED_KEY, SwingHtmlUtil.isUnderline(elem));
            

            elem = document.getParagraphElement(dot);
            set = elem.getAttributes();

            HTMLDocument doc = (HTMLDocument) textArea.getDocument();
            Element htmlElem = doc.getCharacterElement(event.getDot());
            
            HTML.Tag paragraph = SwingHtmlUtil.getParagraph(htmlElem);
//            System.out.println("tag: " + paragraph);
            
            ParagraphType paragraphType = ParagraphType.lookup(paragraph);
            
//            actionMap.get("huxley-paragraph").putValue(MasterEnumSelectAction.SELECTED_VALUE_KEY, paragraphType);
            
            AttributeSet attributes = htmlElem.getAttributes();
            attributes.containsAttribute(CSS.Attribute.FONT_WEIGHT, "bold");
            
            TextAction setAlignment = null;
            
            if (SwingHtmlUtil.isLeftAligned(elem)) {
                setAlignment = TextAction.ALIGN_LEFT;
            } else if (SwingHtmlUtil.isRightAligned(elem)) {
                setAlignment = TextAction.ALIGN_RIGHT;
            } else if (SwingHtmlUtil.isCenterAligned(elem)) {
                setAlignment = TextAction.ALIGN_CENTER;
            }
                        
            if (setAlignment != null) {
                System.out.println("alignment " + setAlignment);
                actionMap.get(setAlignment.actionName)
                        .putValue(Action.SELECTED_KEY, true);
            }
//                }
//            });
        }
    }
}
