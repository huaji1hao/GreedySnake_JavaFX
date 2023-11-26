package com.megasnake.model.game;


import com.megasnake.utils.ImageLoader;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.game.SnakeGameController.COLUMNS;
import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Food  {
    private Image foodImage;
    private int foodX;
    private int foodY;
    private static final Random random = new Random();

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
            foodX = random.nextInt(ROWS);
            foodY = random.nextInt(COLUMNS);

            for (int i = 0; i < mySnake.getBodySize(); i++) {
                Point snakePart = mySnake.getBodyPart(i);
                if (snakePart.getX() == foodX && snakePart.getY() == foodY) {
                    isFoodPlaced = false;
                    break;
                }
            }
        } while (!isFoodPlaced);

        String foodImagePath = ImageLoader.FOODS_IMAGE[random.nextInt(ImageLoader.FOODS_IMAGE.length)];
        foodImage = ImageLoader.getImage(foodImagePath);

    }

}
