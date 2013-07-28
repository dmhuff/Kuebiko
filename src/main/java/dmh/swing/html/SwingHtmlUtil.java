/**
 * Kuebiko - SwingHtmlUtil.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.html;

import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;

import dmh.swing.html.constants.ParagraphType;

/**
 * Utilities for making dealing with the Swing text HTML API easier.
 *
 * @author davehuffman
 */
public final class SwingHtmlUtil {
    private SwingHtmlUtil() {
        throw new AssertionError("Cannot be instantiated.");
    }

    public static void refreshJTextComponent(JTextComponent textComponent) {
        // borrowed from metaphase editor
        int pos = textComponent.getCaretPosition();
        textComponent.setText(textComponent.getText());
        textComponent.validate();
        try {
            textComponent.setCaretPosition(pos);
        } catch (IllegalArgumentException e) {
            // swallow the exception
            // seems like a bug in the JTextPane component
            // only happens occasionally when pasting text at the end of a document
            System.err.println(e.getMessage());
        }
    }

    public static HTML.Tag getParagraph(Element element) {
        final List<HTML.Tag> paragraphTags = ParagraphType.getTags();

        HTML.Tag tag = nameOf(element);
        while (!tag.equals(HTML.Tag.BODY)) {
            if (paragraphTags.contains(tag)) {
                return tag;
            }
            element = element.getParentElement();
            tag = nameOf(element);
        }
        return null;
    }

    public static HTML.Tag nameOf(Element element) {
        return (HTML.Tag) element.getAttributes().getAttribute(AttributeSet.NameAttribute);
    }

    public static boolean containsAttribute(Element element, Object name, Object value) {
        if (element == null) {
            return false;
        }

        Object attribute = element.getAttributes().getAttribute(name);

        if (attribute == null) {
            return containsAttribute(element.getParentElement(), name, value);
        }
        return value.equals(attribute.toString());
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
    }

    public static boolean isCenterAligned(Element element) {
        return containsAttribute(element, HTML.Attribute.ALIGN, "center");
    }
}
