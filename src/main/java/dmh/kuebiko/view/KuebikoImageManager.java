/**
 * Kuebiko - KuebikoImageManager.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.kuebiko.view;

import dmh.util.ImageManager;

/**
 * Singleton manager for application images.
 *
 * @author davehuffman
 */
public final class KuebikoImageManager extends ImageManager {
	private static final ImageManager INSTANCE = new KuebikoImageManager();

    /**
     * @return The singleton instance of the class.
     */
    static ImageManager get() {
        return INSTANCE;
    }
}
