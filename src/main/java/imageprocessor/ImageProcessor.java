package imageprocessor;

import imageprocessor.BadAttributesException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    public void processImage(BufferedImage image, ResizerApp resizerApp) throws IOException, BadAttributesException {

        var builder = Thumbnails.of(image);

        if ((resizerApp.getResize() == null || resizerApp.getResize())
                && resizerApp.getReducedPreviewWidth() != null && resizerApp.getReducedPreviewHeight() != null) {
            builder.size(resizerApp.getReducedPreviewWidth(), resizerApp.getReducedPreviewHeight());
        }
        else builder.size(image.getWidth(), image.getHeight());

        if (resizerApp.getQuality() != null)
            builder.outputQuality(resizerApp.getQuality() / 100.0);

        builder.keepAspectRatio(false);
        builder.toFile(resizerApp.getOutputFile());
    }
}
