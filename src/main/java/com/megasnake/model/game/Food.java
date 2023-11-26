package com.megasnake.model.game;


import com.megasnake.utils.ImageLoader;
import javafx.scene.image.Image;

import java.awt.*;

import static com.megasnake.controller.game.SnakeGameController.COLUMNS;
import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Food  {
    private Image foodImage;
    private int foodX;
    private int foodY;

    public Image getFoodImage() {
        return foodImage;
    }

    public int getX() {
        return foodX;
    }

    public int getY() {
        return foodY;
    }

    public void generateFood(Snake mySnake) {
        boolean isFoodPlaced;

        do {
            isFoodPlaced = true;
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            for (int i = 0; i < mySnake.getBodySize(); i++) {
                Point snakePart = mySnake.getBodyPart(i);
                if (snakePart.getX() == foodX && snakePart.getY() == foodY) {
                    isFoodPlaced = false;
                    break;
                }
            }
        } while (!isFoodPlaced);

        String foodImagePath = ImageLoader.FOODS_IMAGE[(int) (Math.random() * ImageLoader.FOODS_IMAGE.length)];
        foodImage = ImageLoader.getImage(foodImagePath);

    }

}
