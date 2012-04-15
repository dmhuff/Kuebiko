/**
 * Junk - TextAction.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley.constant;

/** 
 * Enumeration of supported text styles.
 *
 * @see javax.swing.text.DefaultEditorKit
 * @see javax.swing.text.StyledEditorKit#defaultActions
 * @see javax.swing.text.html.HTMLEditorKit#defaultActions
 */
public enum TextAction {
    HEADER_1("heading-1"),
    HEADER_2("heading-2"),
    HEADER_3("heading-3"),

    FONT_SIZE_BIGGER("font-size-up"),
    FONT_SIZE_SMALLER("font-size-down"),
    
    INSERT_DATE("date"),
    INSERT_LINK("link"),
    INSERT_LIST_BULLETTED("list-bullets"),
    INSERT_LIST_NUMBERED("list-numbers"),
    INSERT_SEPERATOR("horizontal-rule"),
    INSERT_TABLE("table");
    
    public final String actionName;
    
    private TextAction(String actionName) {
        this.actionName = actionName;
    }
}
