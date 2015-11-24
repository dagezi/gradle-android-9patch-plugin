package io.github.dagezi.ninepatch;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NinePatchCreator {
    final NinePatch ninePatch;
    final File inputFile;
    final File outputDir;

    final BufferedImage inputImage;

    File outputFile;
    BufferedImage outputImage;

    double zoom;

    public NinePatchCreator(NinePatch ninePatch, File inputFile, File outputDir)
            throws IOException {
        this.ninePatch = ninePatch;
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        init();

        inputImage = ImageIO.read(inputFile);
    }

    private void init() {
        outputFile = new File(outputDir, String.format("%s.9.png", ninePatch.name));
        zoom = 1.0; // TODO: extract from inputFile
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
