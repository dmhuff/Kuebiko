/**
 * Kuebiko - ImageManager.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */
package dmh.util;

import java.awt.Image;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Maps;

/**
 * Manager for application images. By default, base classes will read images
 * from a subpackage called "images", with further images representing image
 * size; for example: "images.small.super_cool_icon.png".
 *
 * @author davehuffman
 */
public abstract class ImageManager {
    public enum ImageSize { SMALL /*, BIG*/ } // Big images are not yet implemented.

    private final String imagePackage;
    private final Map<ImageSize, Map<String, Image>> imageCache;
    private ImageSize defaultSize;

    /**
     * Default constructor.
     */
    protected ImageManager() {
    	this("images");
    }

    /**
     * Constructor.
     * @param imagePackage A custom package for images, relative to the base class.
     */
    protected ImageManager(String imagePackage) {
    	this.imagePackage = imagePackage;
        imageCache = Maps.newHashMap();
        for (ImageSize size: ImageSize.values()) {
            Map<String, Image> sizeMap = Maps.newHashMap();
            imageCache.put(size, sizeMap);
        }

        defaultSize = ImageSize.SMALL;
    }

    /**
     * Retrieve an image from the buffer.
     * @param appImage Identifier for the desired image.
     * @return The requested image.
     */
    public Image getImage(String appImage) {
        return getImage(defaultSize, appImage);
    }

    /**
     * Retrieve an image from the buffer.
     * @param size The size of the desired image.
     * @param appImage The image to load.
     * @return The requested image.
     */
    private Image getImage(ImageSize size, String appImage) {
        Image image = imageCache.get(size).get(appImage);
        if (image == null) {
            // If this is the first time the client has requested this image,
            // we'll need to load it.
            image = loadImage(size, appImage);
            imageCache.get(size).put(appImage, image);
        }
        return image;
    }

    /**
     * Load an image.
     * @param size The size of the desired image.
     * @param appImage The image to load.
     * @return The loaded image.
     */
    private Image loadImage(ImageSize size, String appImage) {
        final String path = String.format("%s/%s/%s.png",
                imagePackage, size.toString().toLowerCase(),
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

    public void setDefaultSize(ImageSize defaultSize) {
        this.defaultSize = defaultSize;
    }
}
