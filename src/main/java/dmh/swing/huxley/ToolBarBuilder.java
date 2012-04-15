/**
 * Junk - ToolBarBuilder.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;

import dmh.kuebiko.Main;
import dmh.swing.enumselect.EnumSelectable;
import dmh.swing.enumselect.MasterEnumSelectAction;
import dmh.swing.html.constants.ParagraphType;
import dmh.swing.huxley.constant.TextAction;

/**
 * Builder object for the Huxley UI tool bar.
 *
 * @author davehuffman
 */
class ToolBarBuilder {
    static JToolBar build(HuxleyUiManager huxleyUiManager) {
        return new ToolBarBuilder(huxleyUiManager).buildToolBar();
    }
    
    private final HuxleyUiManager huxleyUiManager;
    
    private ToolBarBuilder(HuxleyUiManager huxleyUiManager) {
        this.huxleyUiManager = huxleyUiManager;
    }

    private JToolBar buildToolBar() {
        JToolBar editToolBar = new JToolBar();
        editToolBar.setFocusable(false);
        editToolBar.setFloatable(false);
        editToolBar.setRollover(true);
        editToolBar.setMargin(new Insets(0, 0, 0, 0));
        
        
        editToolBar.add(newBtn(TextAction.HEADER_1));
        editToolBar.add(newBtn(TextAction.HEADER_2));
        editToolBar.add(newBtn(TextAction.HEADER_3));
        editToolBar.addSeparator();
        editToolBar.add(newBtn(TextAction.INSERT_LINK));
        editToolBar.add(newBtn(TextAction.INSERT_DATE));
        
        return editToolBar;
    }
    
    private JButton newBtn(TextAction action) {
        JButton button = new JButton(huxleyUiManager.getTextAction(action));
        button.setText(null);
//        button.setPreferredSize(new Dimension(16, 16));
        button.setFocusable(false);
        return button;
    }
    
//    private void describeSelection() {
//        String selectedText = instrumentedTextArea.getSelectedText();
//        if (selectedText != null) {
//            selectedText = selectedText.replaceAll("\n", "\\\\n");
//        }
//        
//        System.out.printf("%d-%d:[%s]%n", 
//                instrumentedTextArea.getSelectionStart(),
//                instrumentedTextArea.getSelectionEnd(),
//                selectedText);
//        
//    }
}
