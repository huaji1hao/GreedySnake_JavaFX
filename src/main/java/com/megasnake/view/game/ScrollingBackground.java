package com.megasnake.view.game;

import com.megasnake.utils.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.megasnake.controller.game.SnakeGameController.HEIGHT;
import static com.megasnake.controller.game.SnakeGameController.WIDTH;

public class ScrollingBackground {
    private final Image backgroundImage;
    private double y1;
    private double y2;
    private static final double SCROLL_SPEED = 0.5;
    private static final int IMAGE_SIZE = 256; // 背景图像的尺寸

    public ScrollingBackground() {
        backgroundImage = ImageLoader.getImage("/background/deep_blue_star.png");
        y1 = 0;
        y2 = -IMAGE_SIZE;
    }

    public void draw(GraphicsContext gc) {
        for (int x = 0; x < WIDTH; x += IMAGE_SIZE) {
            for (int y = 0; y < HEIGHT; y += IMAGE_SIZE) {
                gc.drawImage(backgroundImage, x, y1 + y);
                gc.drawImage(backgroundImage, x, y2 + y);
            }
        }

        // 更新背景位置
        y1 += SCROLL_SPEED;
        y2 += SCROLL_SPEED;

        // 当一个背景完全滚动出屏幕时，重置其位置
        if (y1 >= IMAGE_SIZE) {
            y1 = -IMAGE_SIZE;
        }
        if (y2 >= IMAGE_SIZE) {
            y2 = -IMAGE_SIZE;
        }
    }

}
