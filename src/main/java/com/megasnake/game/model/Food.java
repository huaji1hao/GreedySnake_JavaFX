package com.megasnake.game.model;


import javafx.scene.image.Image;

import java.awt.*;

import static com.megasnake.game.controller.SnakeGameController.COLUMNS;
import static com.megasnake.game.controller.SnakeGameController.ROWS;

public class Food  {
    private final String[] FOODS_IMAGE = new String[]{"food/food-apple.png", "food/food-banana.png", "food/food-blueberry.png",
            "food/food-cherry.png", "food/food-durian.png", "food/food-grape.png", "food/food-grapefruit.png", "food/food-kiwi.png",
            "food/food-lemon.png", "food/food-litchi.png", "food/food-mango.png", "food/food-orange.png", "food/food-peach.png",
            "food/food-pear.png", "food/food-pineapple.png", "food/food-pitaya.png", "food/food-strawberry.png", "food/food-watermelon.png"};

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

        try{
            foodImage = new Image(getClass().getResource("/" + FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading food image: " + e.getMessage());
        }

    }

}
