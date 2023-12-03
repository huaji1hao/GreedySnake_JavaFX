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

/**
 * The view class for the game screen.
 *
 * @Author Junfeng ZHU
 */
public class GameView {
    private final GraphicsContext gc;
    private final ScrollingBackground scrollingBackground;

    /**
     * Creates a new GameView object.
     * @param gc The graphics context of the game screen.
     */
    public GameView(GraphicsContext gc) {
        this.gc = gc;
        scrollingBackground = new ScrollingBackground();
    }

    /**
     * Draws the game screen.
     * @param difficulty The difficulty of the game.
     */
    private void drawBackground(int difficulty){
        if(difficulty == 0){
            // Draw the background squares in alternating colors to create a checkerboard pattern
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

    /**
     * Draws the food on the game screen.
     * @param foods The food to be drawn.
     */
    private void drawFood(Food[] foods){
        for (Food food : foods)
            gc.drawImage(food.getFoodImage(), 1.0 * food.getX() * SQUARE_SIZE, 1.0 * food.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Draws the snake on the game screen.
     * @param snake The snake to be drawn.
     * @param currentDirection The current direction of the snake.
     */
    private void drawSnakeBase(Snake snake, int currentDirection){
        // Don't draw the snake if it has no body parts
        if(snake.getBodySize() <= 1) return;

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



    /**
     * Draws the score on the game screen.
     * @param score The score to be drawn.
     */
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

    /**
     * Draws the AI score on the game screen.
     * @param aiScore The AI score to be drawn.
     */
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

    /**
     * Draws all the game objects on the game screen.
     * @param mySnake The player's snake.
     * @param foods The food to be drawn.
     * @param difficulty The difficulty of the game.
     * @param meteor The meteor to be drawn.
     * @param gem The gem to be drawn.
     * @param coin The coin to be drawn.
     * @param aiSnake The AI snake.
     */
    public void drawAll(MySnake mySnake, Food[] foods, int difficulty, Meteor meteor, Gem gem, Coin coin, AISnake aiSnake){
        drawBackground(difficulty);

        // Draw the score under the other game objects
        drawScore(mySnake.getScore());
        if(SnakeGameController.getIsAISnake()) drawAIScore(aiSnake.getScore());

        drawFood(foods);
        drawSnakeBase(mySnake, KeyEventHandler.getCurrentDirection());

        // Draw the meteor, gem, and coin if the playable feature is enabled
        if(SnakeGameController.getIsPlayableFeature()){
            drawSnakeObject(meteor);
            drawSnakeObject(gem);
            drawSnakeObject(coin);
        }

        // Draw the AI snake if the AI snake feature is enabled
        if(SnakeGameController.getIsAISnake()) drawSnakeBase(aiSnake, aiSnake.getCurrentDirection());
    }


    /**
     * Draws a snake object on the game screen.
     * @param snakeObject The snake object to be drawn.
     */
    private void drawSnakeObject(SnakeObject snakeObject) {
        // Calculate the position with the moveFrame applied
        double moveFrame = snakeObject.getMoveFrame();
        double objectX = (snakeObject.getX() + moveFrame * snakeObject.getHorizontalDirection()) * SQUARE_SIZE;
        double objectY = (snakeObject.getY() + moveFrame) * SQUARE_SIZE;
        double rotationAngle = snakeObject.getRotationAngle();

        // Save the current state of the graphics context
        gc.save();

        // Translate the rotation center to the center of the meteor
        gc.translate(objectX + SQUARE_SIZE / 2.0, objectY + SQUARE_SIZE / 2.0);

        // Apply the rotation around the new origin (meteor's center)
        gc.rotate(rotationAngle);

        // Translate the graphics context back to its original position
        gc.translate(-objectX - SQUARE_SIZE / 2.0, -objectY - SQUARE_SIZE / 2.0);

        // Draw the meteor image at its position
        gc.drawImage(snakeObject.getImage(), objectX, objectY, SQUARE_SIZE, SQUARE_SIZE);

        // Restore the graphics context to its original state
        gc.restore();
    }

    /**
     * Draws the game over screen.
     */
    public void drawGameOver() {
        setFont(70);

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeText("Game Over", WIDTH / 5.0, HEIGHT / 2.0);

        gc.setFill(Color.WHITE);
        gc.fillText("Game Over", WIDTH / 5.0, HEIGHT / 2.0);
    }

    /**
     * sets the font of the game screen.
     */
    private void setFont(int fontSize) {
        // Load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), fontSize);
        // Use a default font if custom font fails to load
        gc.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }

}
