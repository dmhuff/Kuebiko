/**
 * Kuebiko - ImageManager.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package com.jcap.view.icons;

import java.awt.Image;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Maps;

/**
 * Singleton manager for application images.
 *
 * @author davehuffman
 */
public final class ImageManager {
    public enum ImageSize { SMALL /*, BIG*/ } // TODO implement big images.
    
    public enum AppImage {
        EDIT, SEARCH
    }
    
    private static final ImageManager INSTANCE = new ImageManager();
    
    /**
     * @return The singleton instance of the class.
     */
    public static ImageManager get() {
        return INSTANCE;
    }
    
    private final Map<ImageSize, Map<AppImage, Image>> images;
    private ImageSize defaultSize;
    
    /**
     * Private constructor, since this is a singleton class. Assigns default
     * state.
     */
    private ImageManager() {
        images = Maps.newHashMap();
        for (ImageSize size: ImageSize.values()) {
            Map<AppImage, Image> sizeMap = Maps.newHashMap();
            images.put(size, sizeMap);
        }
        
        defaultSize = ImageSize.SMALL;
    }
    
    /**
     * Retrieve an image from the buffer.
     * @param appImage Identifier for the desired image.
     * @return The requested image.
     */
    public Image getImage(AppImage appImage) {
        return getImage(defaultSize, appImage);
    }
    
    /**
     * Retrieve an image from the buffer.
     * @param size The size of the desired image.
     * @param appImage The image to load.
     * @return The requested image.
     */
    private Image getImage(ImageSize size, AppImage appImage) {
        Image image = images.get(size).get(appImage);
        if (image == null) {
            // If this is the first time the client has requested this image, 
            // we'll need to load it.
            image = loadImage(size, appImage);
            images.get(size).put(appImage, image);
        }
        return image;
    }

    /**
     * Load an image.
     * @param size The size of the desired image.
     * @param appImage The image to load.
     * @return The loaded image.
     */
    private Image loadImage(ImageSize size, AppImage appImage) {
        final String path = String.format("%s/%s.png",
                size.toString().toLowerCase(), 
                appImage.toString().toLowerCase());
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            // Pass the exception up the stack; these sorts of errors in a
            // deployed application indicate a serious issue.
            throw new RuntimeException(String.format(
                    "Couldn't load image at path [%s].", path), e);
        }
    }

    public void setDefaultSize(ImageSize defaultSize) {
        this.defaultSize = defaultSize;
    }
}
