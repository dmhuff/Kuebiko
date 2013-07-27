/**
 * Kuebiko - Paragraph.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.html.constants;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import dmh.swing.html.HasHtmlTag;

/**
 * Enumeration of paragraph formatting types.
 * @author davehuffman
 */
public enum ParagraphType implements HasHtmlTag {
    PARAGRAPH_FORMAT("Normal", null),
    HEADING_1("Heading 1", Tag.H1),
    HEADING_2("Heading 2", Tag.H2),
    HEADING_3("Heading 3", Tag.H3),
    HEADING_4("Heading 4", Tag.H4),
    HEADING_5("Heading 5", Tag.H5),
    HEADING_6("Heading 6", Tag.H6),
    FORMATTED("Formatted", Tag.PRE);

    /**
     * @return A list of all supported HTML tags.
     */
    public static List<HTML.Tag> getTags() {
        return Lists.transform(Arrays.asList(values()),
                new Function<ParagraphType, HTML.Tag>() {
                    @Override
                    public Tag apply(ParagraphType input) {
                        return input.tag;
                    }
                });
    }

    public static ParagraphType lookup(HTML.Tag tag) {
        for (ParagraphType paragraphType: values()) {
            if (paragraphType.tag != null && paragraphType.tag.equals(tag)) {
                return paragraphType;
            }
        }
        return PARAGRAPH_FORMAT;
    }

    public final String text;
    public final Tag tag;

    ParagraphType(String text, Tag tag) {
        this.text = text;
        this.tag = tag;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getActionName() {
        return "dmh-html-" + toString();
    }
}
