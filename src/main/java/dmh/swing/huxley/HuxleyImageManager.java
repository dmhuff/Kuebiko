/**
 * Kuebiko - KuebikoImageManager.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * TODO license info.
 */
package dmh.swing.huxley;

import dmh.util.ImageManager;

/**
 * Singleton manager for Huxley images.
 *
 * @author davehuffman
 */
public final class HuxleyImageManager extends ImageManager {
	private static final ImageManager INSTANCE = new HuxleyImageManager();

    /**
     * @return The singleton instance of the class.
     */
    public static ImageManager get() {
        return INSTANCE;
    }
}
