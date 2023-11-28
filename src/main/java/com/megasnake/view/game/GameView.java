package com.megasnake.view.game;

import com.megasnake.controller.SnakeGameController;
import com.megasnake.model.*;
import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.KeyEventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

import static com.megasnake.controller.SnakeGameController.*;

public class GameView {
    private final GraphicsContext gc;
    private final ScrollingBackground scrollingBackground;

    public GameView(GraphicsContext gc) {
        this.gc = gc;
        scrollingBackground = new ScrollingBackground();
    }
    public void drawBackground(int difficulty){
        if(difficulty == 0){
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if ((i + j) % 2 == 0) {
                        gc.setFill(Color.web("AAD751"));
                    } else {
                        gc.setFill(Color.web("A2D149"));
                    }
                    gc.fillRect(1.0 * i * SQUARE_SIZE, 1.0 * j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        } else if(difficulty == 1){
            gc.drawImage(ImageLoader.getImage("/background/candy_background.png"), 0, 0, WIDTH, HEIGHT);
        } else if(difficulty == 2){
            gc.drawImage(ImageLoader.getImage("/background/lava_background.png"), 0, 0, WIDTH, HEIGHT);
        } else if(difficulty == 3){
            scrollingBackground.draw(gc);
        }
    }

    public void drawFood(Food[] foods){
        for (Food food : foods)
            gc.drawImage(food.getFoodImage(), 1.0 * food.getX() * SQUARE_SIZE, 1.0 * food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    public void drawSnakeBase(SnakeBase snake, int currentDirection){
        if(snake.getBodySize() <= 1) return;// Don't draw the snake if it has no body parts
        // Calculate the movement frame which determines how much the snake has progressed towards the next cell
        double moveFrame = snake.getMoveFrame();
        // Draw each body part of the snake, starting from the tail and moving towards the head
        for (int i = snake.getBodySize() - 1; i > 1; i--) {
            // Get the current body part
            Point currentPart = snake.getBodyPart(i);
            Point prevPart = snake.getBodyPart(i - 1);

            // Calculate the position with the moveFrame applied
            double partX = currentPart.getX() + (prevPart.getX() - currentPart.getX()) * moveFrame;
            double partY = currentPart.getY() + (prevPart.getY() - currentPart.getY()) * moveFrame;

            // Draw the current body part with a slight reduction in size for visual fineness
            gc.drawImage(snake.getBodyImage(), partX * SQUARE_SIZE, partY * SQUARE_SIZE, SQUARE_SIZE - 1.0, SQUARE_SIZE - 1.0);
        }

        // Get the first part of the snake for drawing
        Point headPart = snake.getBodyPart(1);

        // Calculate the head's new position based on the current direction and moveFrame
        double headX = headPart.getX();
        double headY = headPart.getY();

        // Draw the snake head with the correct orientation based on the current direction of movement
        switch (currentDirection) {
            case KeyEventHandler.RIGHT -> headX += moveFrame;
            case KeyEventHandler.LEFT -> headX -= moveFrame;
            case KeyEventHandler.UP -> headY -= moveFrame;
            case KeyEventHandler.DOWN -> headY += moveFrame;
            default -> System.out.println("Invalid direction: " + currentDirection);
        }
        gc.drawImage(snake.getHeadImage(currentDirection), headX * SQUARE_SIZE, headY * SQUARE_SIZE, SQUARE_SIZE - 1.0, SQUARE_SIZE - 1.0);

    }



    private void drawScore(int score) {
        setFont(35);

        // Draw the text outline in black
        gc.setStroke(Color.BLACK); // Set the outline color to black
        gc.setLineWidth(2); // Set the outline width
        gc.strokeText("Your Score: " + score, 10, 35); // Draw the text outline

        // Draw the filled text in white at the same position
        gc.setFill(Color.WHITE); // Set the fill color to white
        gc.fillText("Your Score: " + score, 10, 35); // Draw the filled text
    }

    private void drawAIScore(int aiScore){
        setFont(35);

        // Draw the text outline in black
        gc.setStroke(Color.WHITE); // Set the outline color to black
        gc.setLineWidth(2); // Set the outline width
        gc.strokeText("AI Score: " + aiScore, 402, 35); // Draw the text outline

        // Draw the filled text in white at the same position
        gc.setFill(Color.BLACK); // Set the fill color to white
        gc.fillText("AI Score: " + aiScore, 402, 35); // Draw the filled text
    }

    public void drawAll(Snake mySnake, Food[] foods, int difficulty, Meteor meteor, Gem gem, Coin coin, AISnake eater){
        drawBackground(difficulty);
        drawScore(mySnake.getScore());
        if(SnakeGameController.getIsAISnake()) drawAIScore(eater.getScore());

        drawFood(foods);
        drawSnakeBase(mySnake, KeyEventHandler.getCurrentDirection());

        if(SnakeGameController.getIsPlayableFeature()){
            drawSnakeObject(meteor);
            drawSnakeObject(gem);
            drawSnakeObject(coin);
        }

        if(SnakeGameController.getIsAISnake()) drawSnakeBase(eater, eater.getCurrentDirection());
    }



    private void drawSnakeObject(SnakeObject snakeObject) {
        double moveFrame = snakeObject.getMoveFrame();
        double objectX = (snakeObject.getX() + moveFrame * snakeObject.getHorizontalDirection()) * SQUARE_SIZE;
        double objectY = (snakeObject.getY() + moveFrame) * SQUARE_SIZE;
        double rotationAngle = snakeObject.getRotationAngle();

        // Save the current state of the graphics context
        gc.save();

        // Translate the rotation center to the center of the meteor
        // This is done by moving the origin of the graphics context to the meteor's center
        gc.translate(objectX + SQUARE_SIZE / 2.0, objectY + SQUARE_SIZE / 2.0);

        // Apply the rotation around the new origin (meteor's center)
        gc.rotate(rotationAngle);

        // Translate the graphics context back to its original position
        // This ensures that the rotation only affects the meteor image
        gc.translate(-objectX - SQUARE_SIZE / 2.0, -objectY - SQUARE_SIZE / 2.0);

        // Draw the meteor image at its position
        gc.drawImage(snakeObject.getImage(), objectX, objectY, SQUARE_SIZE, SQUARE_SIZE);

        // Restore the graphics context to its original state
        // This is important to not affect other elements that will be drawn later
        gc.restore();
    }



    public void drawGameOver() {
        setFont(70);

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeText("Game Over", WIDTH / 5.0, HEIGHT / 2.0);

        gc.setFill(Color.WHITE);
        gc.fillText("Game Over", WIDTH / 5.0, HEIGHT / 2.0);
    }

    private void setFont(int fontSize) {
        // Load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), fontSize);
        // Use a default font if custom font fails to load
        gc.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }

}
