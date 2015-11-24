package io.github.dagezi.ninepatch;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class NinePatchCreator {
    static final Pattern DPI_PATTERN = Pattern.compile("[a-z]+dpi");
    static final Map<String, Double> STRING_DPI_MAP = new HashMap<String, Double>() {
        {
            put("ldpi", 0.75);
            put("mdpi", 1.0);
            put("hdpi", 1.5);
            put("xhdpi", 2.0);
            put("xxhdpi", 3.0);
            put("xxxhdpi", 4.0);
        }
    };

    final NinePatch ninePatch;
    final File inputFile;
    final File outputDir;

    final BufferedImage inputImage;

    File outputFile;
    BufferedImage outputImage;

    double zoom = 1.0;

    public NinePatchCreator(NinePatch ninePatch, File inputFile, File outputDir)
            throws IOException {
        this.ninePatch = ninePatch;
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        init();

        inputImage = ImageIO.read(inputFile);
    }

    private void init() {
        String parentDirName = inputFile.getParentFile().getName();
        Matcher matcher = DPI_PATTERN.matcher(parentDirName);
        if (matcher.find()) {
            zoom = STRING_DPI_MAP.get(matcher.group());
        }
        outputFile = new File(outputDir,
                String.format("%s/%s.9.png", parentDirName, ninePatch.name));
    }

    public void create() throws IOException {
        // TODO: process

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        outputImage = new BufferedImage(width + 2, height + 2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = outputImage.createGraphics();

        graphics.drawImage(inputImage, 1, 1, null);

        outputFile.getParentFile().mkdirs();
        ImageIO.write(outputImage, "png", outputFile);
    }
}
