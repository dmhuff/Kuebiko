/**
 * Junk - HasHtmlTag.java
 * Copyright 2011 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.swing.html;

import javax.swing.text.html.HTML.Tag;

/**
 * Interface for objects that represent a HTML tag.
 *
 * @author davehuffman
 */
public interface HasHtmlTag {
    /** @return The HTML {@link Tag} that this object represents. */
    public Tag getTag();
}
