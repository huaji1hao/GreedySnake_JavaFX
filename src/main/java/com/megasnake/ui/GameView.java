package com.megasnake.ui;

import com.megasnake.model.Food;
import com.megasnake.model.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

import static com.megasnake.controller.GameLogic.*;
import static com.megasnake.util.DirectionHandler.*;

public class GameView {
    public void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("AAD751"));
                } else {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    public void drawFood(GraphicsContext gc, Food food){
        gc.drawImage(food.getFoodImage(), food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    public void drawSnake(GraphicsContext gc, Snake mySnake){

        // The fineness level of the body, used to make the body parts slightly smaller than the square size
        int bodyFineness = 1;

        // Draw each body part of the snake, starting from the tail and moving towards the head
        for (int i = mySnake.getBodySize() - 1; i > 0; i--) {
            // Get the current body part
            Point currentPart = mySnake.getBodyPart(i);
            // Draw the current body part with a slight reduction in size for visual fineness
            gc.drawImage(new Image("snake-body.png"), currentPart.getX() * SQUARE_SIZE, currentPart.getY() * SQUARE_SIZE, SQUARE_SIZE - bodyFineness, SQUARE_SIZE - bodyFineness);

            // Get the previous body part to calculate the mid-point
            Point prevPart = mySnake.getBodyPart(i - 1);
            // Calculate the mid-point between the current and previous body parts
            double midX = (currentPart.getX() + prevPart.getX()) / 2.0;
            double midY = (currentPart.getY() + prevPart.getY()) / 2.0;
            // Draw an additional body part at the mid-point for a smoother snake body appearance
            gc.drawImage(new Image("snake-body.png"), midX * SQUARE_SIZE, midY * SQUARE_SIZE, SQUARE_SIZE - bodyFineness, SQUARE_SIZE - bodyFineness);
        }

        // Get the head part of the snake for drawing
        Point headPart = mySnake.getSnakeHead();
        // Draw the snake head with the correct orientation based on the current direction of movement
        switch (getCurrentDirection()) {
            case RIGHT:
                // Draw the head facing right
                gc.drawImage(new Image("snake-head-right.png"), headPart.getX() * SQUARE_SIZE, headPart.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case LEFT:
                // Draw the head facing left
                gc.drawImage(new Image("snake-head-left.png"), headPart.getX() * SQUARE_SIZE, headPart.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case UP:
                // Draw the head facing up
                gc.drawImage(new Image("snake-head-up.png"), headPart.getX() * SQUARE_SIZE, headPart.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case DOWN:
                // Draw the head facing down
                gc.drawImage(new Image("snake-head-down.png"), headPart.getX() * SQUARE_SIZE, headPart.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
        }
    }


    public void drawScore(GraphicsContext gc, int score) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public void drawAll(GraphicsContext gc, Snake mySnake, Food food){
        drawBackground(gc);
        drawFood(gc, food);
        drawSnake(gc, mySnake);
        drawScore(gc, mySnake.getScore());
    }

    public void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Digital-7", 70));
        gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
    }

}
