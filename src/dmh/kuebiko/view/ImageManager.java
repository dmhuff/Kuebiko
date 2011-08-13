/**
 * Kuebiko - ImageManager.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import java.awt.Image;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Maps;

/**
 * Singleton manager for application images.
 *
 * @author davehuffman
 */
final class ImageManager {
    enum ImageSize { SMALL /*, BIG*/ } // TODO implement big images.
    
    /** XXX This is unmanageable. Change to just lookup images by name (i.e. a String). */
    @Deprecated
    enum AppImage {
        ALIGN_CENTER, ALIGN_JUSTITY, ALIGN_LEFT, ALIGN_RIGHT, BOLD, BREAK, 
        COLOR, EDIT, FONT_SIZE_DOWN, FONT_SIZE_UP, FONT_SIZE, 
        HEADING_1, HEADING_2, HEADING_3, HEADING_4, HEADING_5, HEADING_6, 
        HORIZONTAL_RULE, IMAGE, ITALIC, LINK, LIST_BULLETS, LIST_NUMBERS, SEARCH, 
        STRIKETHROUGH, TABLE, UNDERLINE
    }
    
    private static final ImageManager INSTANCE = new ImageManager();
    
    /**
     * @return The singleton instance of the class.
     */
    static ImageManager get() {
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
    Image getImage(AppImage appImage) {
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
        final String path = String.format("images/%s/%s.png",
                size.toString().toLowerCase(), 
                appImage.toString().toLowerCase().replaceAll("_", "-"));
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            // Pass the exception up the stack; these sorts of errors in a
            // deployed application indicate a serious issue.
            throw new RuntimeException(String.format(
                    "Couldn't load image at path [%s].", path), e);
        }
    }

    void setDefaultSize(ImageSize defaultSize) {
        this.defaultSize = defaultSize;
    }
}
