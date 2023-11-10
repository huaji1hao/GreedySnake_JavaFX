package com.megasnake.model;


import javafx.scene.image.Image;

import java.awt.*;

import static com.megasnake.controller.GameLogic.COLUMNS;
import static com.megasnake.controller.GameLogic.ROWS;

public class Food  {
    public static final String[] FOODS_IMAGE = new String[]{"food-apple.png", "food-banana.png", "food-blueberry.png",
            "food-cherry.png", "food-durian.png", "food-grape.png", "food-grapefruit.png", "food-kiwi.png",
            "food-lemon.png", "food-litchi.png", "food-mango.png", "food-orange.png", "food-peach.png",
            "food-pear.png", "food-pineapple.png", "food-pitaya.png", "food-strawberry.png", "food-watermelon.png"};

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

        foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
    }

}
