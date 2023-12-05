package com.megasnake.model;


import com.megasnake.utils.ImageLoader;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

/**
 * Represents the food that the snake eats.
 *
 * @author Junfeng ZHU
 */
public class Food  {
    private Image foodImage;
    private int foodX;
    private int foodY;
    private static final Random random = new Random();

    /**
     * Gets the image of the food.
     *
     * @return The image of the food.
     */
    public Image getFoodImage() {
        return foodImage;
    }

    public int getX() {
        return foodX;
    }

    public int getY() {
        return foodY;
    }

    /**
     * Generates a new food at a random location on the game board.
     *
     * @param mySnake The food cannot be generated on mySnake's body.
     */
    public void generateFood(MySnake mySnake) {
        boolean isFoodPlaced;

        do {
            isFoodPlaced = true;
            foodX = random.nextInt(ROWS);
            foodY = random.nextInt(COLUMNS);

            // Check if the food is generated on the snake's body
            for (int i = 0; i < mySnake.getBodySize(); i++) {
                Point snakePart = mySnake.getBodyPart(i);
                if (snakePart.getX() == foodX && snakePart.getY() == foodY) {
                    // The food is generated on the snake's body, so generate a new food
                    isFoodPlaced = false;
                    break;
                }
            }
        } while (!isFoodPlaced);

        // Randomly select a food image
        String foodImagePath = ImageLoader.FOODS_IMAGE[random.nextInt(ImageLoader.FOODS_IMAGE.length)];
        foodImage = ImageLoader.getImage(foodImagePath);

    }

}
