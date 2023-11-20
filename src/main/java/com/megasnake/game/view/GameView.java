package com.megasnake.game.view;

import com.megasnake.game.model.Food;
import com.megasnake.game.utils.KeyEventHandler;
import com.megasnake.game.model.Snake;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.AmbientLight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.Objects;

import static com.megasnake.game.controller.SnakeGameController.*;

public class GameView {
    private ScrollingBackground scrollingBackground;


    public GameView() {
        scrollingBackground = new ScrollingBackground();
    }
    public void drawBackground(GraphicsContext gc, int difficulty){
        try{
            if(difficulty == 0){
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
            } else if(difficulty == 1){
                gc.drawImage(new Image(getClass().getResource("/background/candy_background.png").toURI().toString()), 0, 0, WIDTH, HEIGHT);
            } else if(difficulty == 2){
                gc.drawImage(new Image(getClass().getResource("/background/lava_background.jpg").toURI().toString()), 0, 0, WIDTH, HEIGHT);
            } else if(difficulty == 3){
                scrollingBackground.draw(gc);
            }
        }catch (Exception e){
            System.out.println("Error loading background image: " + e.getMessage());
        }
    }

    public void drawFood(GraphicsContext gc, Food food){
        gc.drawImage(food.getFoodImage(), food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    public void drawSnake(GraphicsContext gc, Snake mySnake, int difficulty) {
        try {
            String snakeFolderPath;

            if(difficulty == 1) snakeFolderPath = "/snake/candy-snake/";
            else if(difficulty == 2) snakeFolderPath = "/snake/lava-snake/";
            else if(difficulty == 4) snakeFolderPath = "/snake/plain-snake/";
            else snakeFolderPath = "/snake/plain-snake/";

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
                gc.drawImage(new Image(getClass().getResource(snakeFolderPath + "snake-body.png").toURI().toString()), partX * SQUARE_SIZE, partY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            }

            // Get the first part of the snake for drawing
            Point headPart = mySnake.getBodyPart(1);

            // Calculate the head's new position based on the current direction and moveFrame
            double headX = headPart.getX();
            double headY = headPart.getY();
            int currentDirection = KeyEventHandler.getCurrentDirection();
            switch (currentDirection) {
                case KeyEventHandler.RIGHT -> headX += moveFrame;
                case KeyEventHandler.LEFT -> headX -= moveFrame;
                case KeyEventHandler.UP -> headY -= moveFrame;
                case KeyEventHandler.DOWN -> headY += moveFrame;
            }

            // Draw the snake head with the correct orientation based on the current direction of movement
            switch (currentDirection) {
                case KeyEventHandler.RIGHT ->
                    // Draw the head facing right
                        gc.drawImage(new Image(getClass().getResource(snakeFolderPath + "snake-head-right.png").toURI().toString()), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                case KeyEventHandler.LEFT ->
                    // Draw the head facing left
                        gc.drawImage(new Image(getClass().getResource(snakeFolderPath + "snake-head-left.png").toURI().toString()), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                case KeyEventHandler.UP ->
                    // Draw the head facing up
                        gc.drawImage(new Image(getClass().getResource(snakeFolderPath + "snake-head-up.png").toURI().toString()), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                case KeyEventHandler.DOWN ->
                    // Draw the head facing down
                        gc.drawImage(new Image(getClass().getResource(snakeFolderPath + "snake-head-down.png").toURI().toString()), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            }
        } catch (Exception e) {
            System.out.println("Error loading snake image: " + e.getMessage());
        }

    }



    public void drawScore(GraphicsContext gc, int score) {
        setFont(gc,35);

        // Draw the text outline in black
        gc.setStroke(Color.BLACK); // Set the outline color to black
        gc.setLineWidth(2); // Set the outline width
        gc.strokeText("Score: " + score, 10, 35); // Draw the text outline

        // Draw the filled text in white at the same position
        gc.setFill(Color.WHITE); // Set the fill color to white
        gc.fillText("Score: " + score, 10, 35); // Draw the filled text

    }

    public void drawAll(GraphicsContext gc, Snake mySnake, Food food, int difficulty){
        drawBackground(gc, difficulty);
        drawFood(gc, food);
        drawSnake(gc, mySnake, difficulty);
        drawScore(gc, mySnake.getScore());
    }

    public void drawGameOver(GraphicsContext gc) {
        setFont(gc, 70);

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeText("Game Over", WIDTH / 5, HEIGHT / 2);

        gc.setFill(Color.WHITE);
        gc.fillText("Game Over", WIDTH / 5, HEIGHT / 2);
    }

    private void setFont(GraphicsContext gc, int fontSize) {
        String FONT_PATH = "/font/kenvector_future.ttf"; // Path to the custom font file

        // Load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), fontSize);
        // Use a default font if custom font fails to load
        gc.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }

}
