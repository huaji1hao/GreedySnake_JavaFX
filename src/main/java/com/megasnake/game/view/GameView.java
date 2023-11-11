package com.megasnake.game.view;

import com.megasnake.game.model.Food;
import com.megasnake.game.utils.DirectionHandler;
import com.megasnake.game.model.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

import static com.megasnake.game.controller.GameLogic.*;

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

    public void drawSnake(GraphicsContext gc, Snake mySnake) {
        // The fineness level of the body, used to make the body parts slightly smaller than the square size
        int bodyFineness = 1;

        // Calculate the movement frame which determines how much the snake has progressed towards the next cell
        double moveFrame = mySnake.getMoveFrame();

        // Draw each body part of the snake, starting from the tail and moving towards the head
        for (int i = mySnake.getBodySize() - 1; i > 1; i--) {
            // Get the current body part
            Point currentPart = mySnake.getBodyPart(i);
            Point prevPart = mySnake.getBodyPart(i - 1);

            // Calculate the position with the moveFrame applied
            double partX = currentPart.getX() + (prevPart.getX() - currentPart.getX()) * moveFrame;
            double partY = currentPart.getY() + (prevPart.getY() - currentPart.getY()) * moveFrame;

            // Draw the current body part with a slight reduction in size for visual fineness
            gc.drawImage(new Image("snake-body.png"), partX * SQUARE_SIZE, partY * SQUARE_SIZE, SQUARE_SIZE - bodyFineness, SQUARE_SIZE - bodyFineness);
        }

        // Get the first part of the snake for drawing
        Point headPart = mySnake.getBodyPart(1);

        // Calculate the head's new position based on the current direction and moveFrame
        double headX = headPart.getX();
        double headY = headPart.getY();
        int currentDirection = DirectionHandler.getCurrentDirection();
        if (currentDirection == DirectionHandler.RIGHT) {
            headX += moveFrame;
        } else if (currentDirection == DirectionHandler.LEFT) {
            headX -= moveFrame;
        } else if (currentDirection == DirectionHandler.UP) {
            headY -= moveFrame;
        } else if (currentDirection == DirectionHandler.DOWN) {
            headY += moveFrame;
        }

        // Draw the snake head with the correct orientation based on the current direction of movement
        switch (currentDirection) {
            case DirectionHandler.RIGHT:
                // Draw the head facing right
                gc.drawImage(new Image("snake-head-right.png"), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case DirectionHandler.LEFT:
                // Draw the head facing left
                gc.drawImage(new Image("snake-head-left.png"), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case DirectionHandler.UP:
                // Draw the head facing up
                gc.drawImage(new Image("snake-head-up.png"), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                break;
            case DirectionHandler.DOWN:
                // Draw the head facing down
                gc.drawImage(new Image("snake-head-down.png"), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
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
