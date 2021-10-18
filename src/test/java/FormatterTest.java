import imageprocessor.BadAttributesException;
import imageprocessor.ResizerApp;
import org.junit.jupiter.api.Test;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTest {
    private static final String FILM_COVER_SOURCE_NAME = "Good_Will_Hunting_1997.jpg";
    private static final String FILM_COVER_TARGET_NAME = "Good_Will_Hunting_1997.preview.jpg";
    private static final Integer FILM_COVER_HEIGHT = 1500;
    private static final Integer FILM_COVER_WIDTH = 983;

    private static final String BOOK_COVER_SOURCE_NAME = "J_R_R_Tolkien_The_Hobbit_1937.jpg";
    private static final String BOOK_COVER_TARGET_NAME = "J_R_R_Tolkien_The_Hobbit_1937.preview.jpg";
    private static final Integer BOOK_COVER_HEIGHT = 1980;
    private static final Integer BOOK_COVER_WIDTH = 1400;

    private static final String AUDIO_COVER_SOURCE_NAME = "Metallica_Kill_Em_All_1983.jpeg";
    private static final String AUDIO_COVER_TARGET_NAME = "Metallica_Kill_Em_All_1983.preview.jpeg";
    private static final Integer AUDIO_COVER_HEIGHT = 1425;
    private static final Integer AUDIO_COVER_WIDTH = 1425;

    @Test
    public void testReducingCover() throws Exception {
        final Integer reducedPreviewWidth = FILM_COVER_WIDTH - 800;
        final Integer reducedPreviewHeight = FILM_COVER_HEIGHT - 500;

        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeWidth(reducedPreviewWidth);
        app.setResizeHeight(reducedPreviewHeight);
        app.setQuality(100);
        app.call();

        BufferedImage reducedPreview = ImageIO.read(new File(absolutePathOutput));

        assertEquals(reducedPreview.getWidth(), reducedPreviewWidth);
        assertEquals(reducedPreview.getHeight(), reducedPreviewHeight);
    }

    @Test
    public void testEnlargeCover() throws Exception {
        final Integer reducedPreviewWidth = FILM_COVER_WIDTH + FILM_COVER_WIDTH;
        final Integer reducedPreviewHeight = FILM_COVER_HEIGHT + FILM_COVER_HEIGHT;

        URL res = getClass().getClassLoader().getResource(FILM_COVER_SOURCE_NAME);
        assert res != null;

        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(FILM_COVER_SOURCE_NAME, FILM_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setResizeWidth(reducedPreviewWidth);
        app.setResizeHeight(reducedPreviewHeight);
        app.setQuality(100);
        app.call();

        BufferedImage reducedPreview = ImageIO.read(new File(absolutePathOutput));

        assertEquals(reducedPreview.getWidth(), reducedPreviewWidth);
        assertEquals(reducedPreview.getHeight(), reducedPreviewHeight);
    }

    @Test
    public void testTypoSourceName() throws Exception {
        final String typo = "ops!sic!";

        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput + typo));
        app.setOutputFile(new File(absolutePathOutput));
        IIOException generatedException = null;
        try {
            app.call();
        } catch (IIOException e) {
            generatedException = e;
        }

        assertEquals("Can't read input file!", generatedException.getMessage());
        assertEquals(IIOException.class, generatedException.getClass());
    }

    @Test
    public void testBadAttributes() throws Exception {
        URL res = getClass().getClassLoader().getResource(AUDIO_COVER_SOURCE_NAME);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePathInput = file.getAbsolutePath();

        String absolutePathOutput = absolutePathInput.replaceFirst(AUDIO_COVER_SOURCE_NAME, AUDIO_COVER_TARGET_NAME);

        ResizerApp app = new ResizerApp();
        app.setInputFile(new File(absolutePathInput));
        app.setOutputFile(new File(absolutePathOutput));
        app.setQuality(-50);
        BadAttributesException generatedException = null;
        try {
            app.call();
        } catch (BadAttributesException e) {
            generatedException = e;
        }

        assertEquals("Please check params!", generatedException.getMessage());
        assertEquals(BadAttributesException.class, generatedException.getClass());
    }
}
