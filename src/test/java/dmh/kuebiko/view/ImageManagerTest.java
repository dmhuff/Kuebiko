/**
 * Kuebiko - ImageManagerTest.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */
package dmh.kuebiko.view;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

import java.awt.Image;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import dmh.swing.huxley.HuxleyImageManager;
import dmh.util.ImageManager.ImageSize;

/**
 * TestNG test class for {@link dmh.kuebiko.view.ImageManager}
 *
 * @author davehuffman
 */
public class ImageManagerTest {
    @Test
    public void singletonTest() {
        assertSame(HuxleyImageManager.get(), HuxleyImageManager.get(),
                "There should only be one instance of the ImageManager class");
    }

    @Test
    public void getImageTest() throws Exception {
        for (ImageSize size: ImageSize.values()) {
            HuxleyImageManager.get().setDefaultSize(size);

            Collection<String> imageIds = getImageIds(size);

            for (String imageId: imageIds) {
                final Image image = HuxleyImageManager.get().getImage(imageId);

                assertNotNull(image, "Return value should not be null.");
                assertSame(HuxleyImageManager.get().getImage(imageId), image,
                        "Buffering should return same object for same ID.");
            }
        }
    }

    /**
     * Retrieve a list of all known image ID strings of a particular size.
     * @param size The size of the requested images.
     * @return A list of valid image IDs.
     */
    private Collection<String> getImageIds(ImageSize size)
    throws URISyntaxException {
        String path = String.format("images/%s/",
                size.toString().toLowerCase());
        File imageDir = new File(getClass().getResource(path).toURI());

        Collection<String> imageIds = Lists.newArrayList(Collections2.transform(
                Arrays.asList(imageDir.list()),
                new Function<String, String>() {
                    @Override
                    public String apply(String input) {
                        return "png".equals(FilenameUtils.getExtension(input))?
                                FilenameUtils.removeExtension(input)
                                : "";
                    }
                }));
        imageIds.removeAll(Collections.singleton(""));

        return imageIds;
    }
}
