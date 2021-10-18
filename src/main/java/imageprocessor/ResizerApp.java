package imageprocessor;

import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command (name = "convert", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "Options Settings:", sortOptions = false)
public class ResizerApp implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "input-file", description = "Files whose contents to display")
    private File inputFile;

    @CommandLine.Parameters(paramLabel = "output-file", description = "Files whose contents to display")
    private File outputFile;

    @CommandLine.Option(names = "--resize")
    private Boolean resize;

    @CommandLine.Option(names = "width",required = false, description = "Resize the image", order = 0)
    private Integer reducedPreviewWidth;

    @CommandLine.Option(names = "height",required = false, description = "Resize the image", order = 1)
    private Integer reducedPreviewHeight;

    @CommandLine.Option(names = "--quality", description = "Quality", order = 2)
    private Integer quality;

    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(ImageIO.read(inputFile), this);
        return 0;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setResizeWidth(Integer reducedPreviewWidth) {
        this.reducedPreviewWidth = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) {
        this.reducedPreviewHeight = reducedPreviewHeight;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public Integer getReducedPreviewWidth() throws BadAttributesException {
        checkPositive(reducedPreviewWidth);
        return reducedPreviewWidth;
    }

    public Integer getReducedPreviewHeight() throws BadAttributesException {
        checkPositive(reducedPreviewHeight);
        return reducedPreviewHeight;
    }

    public Integer getQuality() throws BadAttributesException {
        checkPositive(quality);
        return quality;
    }

    public Boolean getResize() {
        return resize;
    }

    private void checkPositive(Integer value) throws BadAttributesException {
        if (value != null && value < 0) throw new BadAttributesException("Please check params!");
    }
}
