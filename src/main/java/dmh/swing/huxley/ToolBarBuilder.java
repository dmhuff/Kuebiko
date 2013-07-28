/**
 * Junk - ToolBarBuilder.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.huxley;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JToolBar;

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
        button.setFocusable(false);
        return button;
    }
}
