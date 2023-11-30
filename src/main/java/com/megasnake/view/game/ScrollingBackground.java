package com.megasnake.view.game;

import com.megasnake.utils.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.megasnake.controller.SnakeGameController.HEIGHT;
import static com.megasnake.controller.SnakeGameController.WIDTH;

/**
 * A class for drawing a scrolling background.
 */
public class ScrollingBackground {
    private final Image backgroundImage;
    private double y1;
    private double y2;
    private static final String BACKGROUND_IMAGE_PATH = "/background/deep_blue_star.png";
    private static final int IMAGE_SIZE = 256;

    /**
     * Creates a new ScrollingBackground object.
     */
    public ScrollingBackground() {
        backgroundImage = ImageLoader.getImage(BACKGROUND_IMAGE_PATH);
        y1 = 0;
        y2 = -IMAGE_SIZE;
    }

    /**
     * Draws the background.
     *
     * @param gc The GraphicsContext to draw on.
     */
    public void draw(GraphicsContext gc) {
        for (int x = 0; x < WIDTH; x += IMAGE_SIZE) {
            for (int y = 0; y < HEIGHT; y += IMAGE_SIZE) {
                gc.drawImage(backgroundImage, x, y1 + y);
                gc.drawImage(backgroundImage, x, y2 + y);
            }
        }

        // Scroll the background
        double scrollSpeed = 0.5;
        y1 += scrollSpeed;
        y2 += scrollSpeed;

        // Reset the background when it goes off-screen
        if (y1 >= IMAGE_SIZE) {
            y1 = -IMAGE_SIZE;
        }
        if (y2 >= IMAGE_SIZE) {
            y2 = -IMAGE_SIZE;
        }
    }

}
