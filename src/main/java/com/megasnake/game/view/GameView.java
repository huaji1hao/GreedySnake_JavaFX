package com.megasnake.game.view;

import com.megasnake.game.controller.SnakeGameController;
import com.megasnake.game.model.Food;
import com.megasnake.game.model.Meteor;
import com.megasnake.game.utils.KeyEventHandler;
import com.megasnake.game.model.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.Objects;

import static com.megasnake.game.controller.SnakeGameController.*;

public class GameView {
    private ScrollingBackground scrollingBackground;
    private static final String SNAKE_BODY_NAME = "snake-body.png";
    private static final String SNAKE_RIGHT_HEAD_NAME = "snake-head-right.png";
    private static final String SNAKE_LEFT_HEAD_NAME = "snake-head-left.png";
    private static final String SNAKE_UP_HEAD_NAME = "snake-head-up.png";
    private static final String SNAKE_DOWN_HEAD_NAME = "snake-head-down.png";


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

    public void drawFood(GraphicsContext gc, Food[] foods){
        for (Food food : foods)
            gc.drawImage(food.getFoodImage(), food.getX() * SQUARE_SIZE, food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    public void drawSnake(GraphicsContext gc, Snake mySnake, int difficulty) {
        try {
            if(mySnake.getBodySize() <= 1) return;// Don't draw the snake if it has no body parts

            String snakeFolderPath = switch (difficulty) {
                case 1 -> "/snake/candy-snake/";
                case 2 -> "/snake/lava-snake/";
                case 3 -> "/snake/space-snake/";
                default -> "/snake/plain-snake/";
            };

            StringBuilder snakeFolderPathBuilder = new StringBuilder();
            snakeFolderPathBuilder.append(snakeFolderPath).append(SNAKE_BODY_NAME);

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
                gc.drawImage(new Image(getClass().getResource(snakeFolderPathBuilder.toString()).toURI().toString()), partX * SQUARE_SIZE, partY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            }

            // Get the first part of the snake for drawing
            Point headPart = mySnake.getBodyPart(1);

            // Calculate the head's new position based on the current direction and moveFrame
            double headX = headPart.getX();
            double headY = headPart.getY();
            int currentDirection = KeyEventHandler.getCurrentDirection();
            StringBuilder headImageBuilder = new StringBuilder();
            headImageBuilder.append(snakeFolderPath);

            // Draw the snake head with the correct orientation based on the current direction of movement
            switch (currentDirection) {
                case KeyEventHandler.RIGHT -> {
                    headX += moveFrame;
                    headImageBuilder.append(SNAKE_RIGHT_HEAD_NAME);
                }
                case KeyEventHandler.LEFT -> {
                    headX -= moveFrame;
                    headImageBuilder.append(SNAKE_LEFT_HEAD_NAME);
                }
                case KeyEventHandler.UP -> {
                    headY -= moveFrame;
                    headImageBuilder.append(SNAKE_UP_HEAD_NAME);
                }
                case KeyEventHandler.DOWN -> {
                    headY += moveFrame;
                    headImageBuilder.append(SNAKE_DOWN_HEAD_NAME);
                }
            }

            gc.drawImage(new Image(getClass().getResource(headImageBuilder.toString()).toURI().toString()), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);

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

    public void drawAll(GraphicsContext gc, Snake mySnake, Food[] foods, int difficulty, Meteor meteor){
        drawBackground(gc, difficulty);
        drawFood(gc, foods);
        drawSnake(gc, mySnake, difficulty);
        drawScore(gc, mySnake.getScore());
        if(SnakeGameController.getIsPlayableFeature())drawMeteor(gc, meteor);
    }

    private void drawMeteor(GraphicsContext gc, Meteor meteor) {
        double moveFrame = meteor.getMoveFrame();
        double meteorX = meteor.getX() * SQUARE_SIZE;
        double meteorY = (meteor.getY() + moveFrame) * SQUARE_SIZE;
        double rotationAngle = meteor.getRotationAngle();

        // Save the current state of the graphics context
        gc.save();

        // Translate the rotation center to the center of the meteor
        // This is done by moving the origin of the graphics context to the meteor's center
        gc.translate(meteorX + SQUARE_SIZE / 2, meteorY + SQUARE_SIZE / 2);

        // Apply the rotation around the new origin (meteor's center)
        gc.rotate(rotationAngle);

        // Translate the graphics context back to its original position
        // This ensures that the rotation only affects the meteor image
        gc.translate(-meteorX - SQUARE_SIZE / 2, -meteorY - SQUARE_SIZE / 2);

        // Draw the meteor image at its position
        gc.drawImage(meteor.getImage(), meteorX, meteorY, SQUARE_SIZE, SQUARE_SIZE);

        // Restore the graphics context to its original state
        // This is important to not affect other elements that will be drawn later
        gc.restore();
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
