/**
 * Kuebiko - SwingHtmlUtil.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import javax.swing.text.Element;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;

/**
 * Utilities for making with the Swing text HTML API easier.
 * @deprecated moved to dmh.swing.html package.
 * @author davehuffman
 */
@Deprecated
public final class SwingHtmlUtil {
    private SwingHtmlUtil() {
        throw new AssertionError("Cannot be instantiated.");
    }
    
//    public static boolean __containsAttribute(AbstractDocument.AbstractElement element, 
//            Object name, Object value) {
//        return value.equals(element.getAttribute(name));
//    }
    
    public static boolean containsAttribute(Element element, 
            Object name, Object value) {
        
        if (element == null) {
            return false;
        }
        
        Object attribute = element.getAttributes().getAttribute(name);
        
        if (attribute == null) {
            return containsAttribute(element.getParentElement(), name, value);
        }
        return value.equals(attribute.toString());
        
        
//        return attribute == null? false : value.equals(attribute.toString());
    }
    
    public static boolean isBold(Element element) {
        return containsAttribute(element, CSS.Attribute.FONT_WEIGHT, "bold");
    }
    
    public static boolean isUnderline(Element element) {
        return containsAttribute(element, CSS.Attribute.TEXT_DECORATION, "underline");
    }
    
    public static boolean isItalic(Element element) {
        return containsAttribute(element, CSS.Attribute.FONT_STYLE, "italic");
    }
    
    public static boolean isStrikethrough(Element element) {
        return containsAttribute(element, CSS.Attribute.TEXT_DECORATION, "line-through");
    }
    
    public static boolean isLeftAligned(Element element) {
        return containsAttribute(element, HTML.Attribute.ALIGN, "left");
    }

    public static boolean isRightAligned(Element element) {
        return containsAttribute(element, HTML.Attribute.ALIGN, "right");
//        return containsAttribute(element, CSS.Attribute.TEXT_ALIGN, "right");
    }
    
    public static boolean isCenterAligned(Element element) {
        return containsAttribute(element, HTML.Attribute.ALIGN, "center");
    }
}
