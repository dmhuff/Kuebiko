/**
 * Junk - TextAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley.constants;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.html.HTMLEditorKit;

/** 
 * Enumeration of supported text styles for notes. Action names are defined 
 * in the Swing library, some of which do not have symbolic constants
 * defined.
 * @see javax.swing.text.DefaultEditorKit
 * @see javax.swing.text.StyledEditorKit#defaultActions
 * @see javax.swing.text.html.HTMLEditorKit#defaultActions
 */
public enum TextAction {
    ALIGN_CENTER("center-justify"),
    ALIGN_JUSTIFY(""), // TODO implement.
    ALIGN_LEFT("left-justify"),
    ALIGN_RIGHT("right-justify"),
    
    HEADER_1("custom-header-1"),

    FONT(""), // TODO implement.
    FONT_COLOR(HTMLEditorKit.COLOR_ACTION),
    FONT_SIZE_BIGGER(HTMLEditorKit.FONT_CHANGE_BIGGER),
    FONT_SIZE_SMALLER(HTMLEditorKit.FONT_CHANGE_SMALLER),
    
    STYLE_BOLD("font-bold"),
    STYLE_ITALIC("font-italic"),
    STYLE_STRIKETHROUGH("font-strikethrough"),
    STYLE_UNDERLINE("font-underline"),
    
    INSERT_BREAK(DefaultEditorKit.insertBreakAction),
    INSERT_IMAGE(""), // TODO implement.
    INSERT_LINK(""), // TODO implement.
    INSERT_LIST_NUMBERED("InsertOrderedListItem"),
    INSERT_LIST_NUMBERED_ITEM("InsertOrderedList"),
    INSERT_LIST_BULLETTED("InsertUnorderedList"),
    INSERT_LIST_BULLETTED_ITEM("InsertUnorderedListItem"),
    INSERT_SEPERATOR("InsertHR"),
    INSERT_TABLE("InsertTable"),
    INSERT_TABLE_COL("InsertTableDataCell"),
    INSERT_TABLE_ROW("InsertTableRow");
    
    /** Identifier of the Swing action associated with this text style. */
    public final String actionName;
    
    private TextAction(String actionName) {
        this.actionName = actionName;
    }
}
