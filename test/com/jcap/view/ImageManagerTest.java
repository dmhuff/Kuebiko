/**
 * jCap - ImageManagerTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.view;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

import java.awt.Image;

import org.testng.annotations.Test;

import com.jcap.view.ImageManager;
import com.jcap.view.ImageManager.AppImage;
import com.jcap.view.ImageManager.ImageSize;

/**
 * TestNG test class for the ImageManager class.
 * @see com.jcap.view.ImageManager
 *
 * @author davehuffman
 */
public class ImageManagerTest {
    @Test
    public void singletonTest() {
        assertSame(ImageManager.get(), ImageManager.get(),
                "There should only be one instance of the ImageManager class");
    }
    
    @Test
    public void getImageTest() {
        for (ImageSize size: ImageSize.values()) {
            ImageManager.get().setDefaultSize(size);
            for (AppImage imageId: AppImage.values()) {
                final Image image = ImageManager.get().getImage(imageId);

                assertNotNull(image, "Return value should not be null.");
                assertSame(ImageManager.get().getImage(imageId), image, 
                        "Buffering should return same object for same ID.");
            }
        }
    }
}
