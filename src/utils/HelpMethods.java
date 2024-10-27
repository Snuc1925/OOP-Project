package utils;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import static utils.Constants.Screen.*;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class HelpMethods {
    public static int getXForCenterText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return SCREEN_WIDTH/2 - length/2;
    }
    public static int getYForCenterText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return SCREEN_HEIGHT/2 - length/2;
    }

    public static Font loadFont(String fontName) {
        Font font = null;
        try {
            InputStream is = HelpMethods.class.getClassLoader().getResourceAsStream("font/" + fontName + ".ttf");
            if (is == null) {
                System.err.println("File not found: " + fontName + ".ttf");
                return null;
            }
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            System.err.println("Font format error: " + fontName + ".ttf");
        } catch (IOException e) {
            System.out.println("Failed to load font: " + fontName + ".ttf");
        }
        return font;
    }

    public static BufferedImage setUp(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(HelpMethods.class.getClassLoader().getResourceAsStream(imagePath + ".png")));
            scaledImage = utilityTool.scaleImage(scaledImage, width, height);

        } catch (IOException e) {
            System.out.println("Failed to load/scale image");
        }
        return scaledImage;
    }
    public static BufferedImage scaleImage(BufferedImage inputImage, double scaleFactor) {
        int width = (int) (inputImage.getWidth() * scaleFactor);
        int height = (int) (inputImage.getHeight() * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(width, height, inputImage.getType());
        Graphics2D g = scaledImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        g.drawRenderedImage(inputImage, at);
        g.dispose();

        return scaledImage;
    }
    public static BufferedImage getBarImage(BufferedImage inputBar, double percentage) {
        int width = (int) (inputBar.getWidth() * percentage);
        int height = inputBar.getHeight();

        BufferedImage scaledImage = new BufferedImage(width, height, inputBar.getType());
        Graphics2D g = scaledImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(percentage, 1.0);
        g.drawRenderedImage(inputBar, at);
        g.dispose();

        return scaledImage;
    }
}
