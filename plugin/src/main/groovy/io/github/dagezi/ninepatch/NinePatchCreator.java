package io.github.dagezi.ninepatch;

import java.awt.Color;
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
        String basename = inputFile.getName();
        String imageName = basename.substring(0, basename.lastIndexOf('.'));

        Matcher matcher = DPI_PATTERN.matcher(parentDirName);
        if (matcher.find()) {
            zoom = STRING_DPI_MAP.get(matcher.group());
        }
        outputFile = new File(outputDir,
                String.format("%s/%s.9.png", parentDirName, imageName));
    }

    public void create() throws IOException {
        if (ninePatch.hStretch.isEmpty()) {
            throw new IllegalArgumentException("At least one hStream must be specified");
        }
        if (ninePatch.vStretch.isEmpty()) {
            throw new IllegalArgumentException("At least one vStream must be specified");
        }

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        outputImage = new BufferedImage(width + 2, height + 2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = outputImage.createGraphics();
        graphics.drawImage(inputImage, 1, 1, null);

        graphics.setColor(Color.black);
        for (NinePatch.Range origRange : ninePatch.vStretch) {
            NinePatch.Range range = origRange.clone();
            range.zoom(zoom);
            range.ensure(height);
            graphics.drawLine(0, range.from + 1, 0, range.to);
        }
        for (NinePatch.Range origRange : ninePatch.hStretch) {
            NinePatch.Range range = origRange.clone();
            range.zoom(zoom);
            range.ensure(width);
            graphics.drawLine(range.from + 1, 0, range.to, 0);
        }
        if (ninePatch.vPadding != null) {
            NinePatch.Range range = ninePatch.vPadding.clone();
            range.zoom(zoom);
            range.ensure(height);
            graphics.drawLine(width + 1, range.from + 1, width + 1, range.to);
        }
        if (ninePatch.hPadding != null) {
            NinePatch.Range range = ninePatch.hPadding.clone();
            range.zoom(zoom);
            range.ensure(width);
            graphics.drawLine(range.from + 1, height + 1, range.to, height + 1);
        }

        outputFile.getParentFile().mkdirs();
        ImageIO.write(outputImage, "png", outputFile);
    }
}
