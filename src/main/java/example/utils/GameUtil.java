package example.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Utility class for game-related image operations.
 */
public class GameUtil {

    /**
     * Retrieves an image from the specified path.
     *
     * @param imagePath The path to the image resource.
     * @return The loaded image, or null if the image cannot be found or read.
     */
    public static Image getImage(String imagePath) {
        URL url = GameUtil.class.getClassLoader().getResource(imagePath);
        if (url == null) {
            System.err.println("Error: Image not found at path: " + imagePath);
            return null;
        }

        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image at path: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rotates the given image by the specified degree.
     *
     * @param bufferedImage The BufferedImage to rotate.
     * @param degree        The degree of rotation.
     * @return The rotated image.
     */
    public static Image rotateImage(final BufferedImage bufferedImage, final int degree) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();

        BufferedImage rotatedImage = new BufferedImage(width, height, type);
        Graphics2D graphics2D = rotatedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree), width / 2.0, height / 2.0);
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();

        return rotatedImage;
    }
}
