package com.megasnake.model;

import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.SoundEffectPlayer;
import com.megasnake.controller.SpeedController;
import com.megasnake.utils.KeyEventHandler;
import javafx.scene.image.Image;
import java.util.ArrayList;

import static com.megasnake.controller.GameController.ROWS;
import static com.megasnake.model.THEME.*;
import static com.megasnake.utils.KeyEventHandler.*;

/**
 * Represents the snake operated by the player.
 *
 * @see Snake
 * @author Sigurður Sigurðardóttir
 * @author Junfeng ZHU-modified
 */
public class MySnake extends Snake {
    private int level;

    /**
     * Constructs a new snake.
     */
    public MySnake() {
        snakeBody = new ArrayList<>();
        // Initialize the snake body
        for (int i = 0; i < 4; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        // Initialize the snake head
        snakeHead = snakeBody.get(0);
        speedController = new SpeedController();
    }

    /**
     * If the snake head collides with the food,
     * the snake will grow its tail and the food will be regenerated.
     *
     * @param food The food to be eaten.
     */
    public void eatFood(Food food) {
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            SoundEffectPlayer.playMusic("/audio/eat.mp3");
            snakeBody.add(new Point(snakeBody.get(snakeBody.size()-1).getX(), snakeBody.get(snakeBody.size()-1).getY()));
            food.generateFood(this);
            addScore(5 * speedController.getSpeedLevel());
        }
    }

    /**
     * If any part of the snake body collides with the meteor,
     * the snake will lose its tail and the meteor will be regenerated.
     *
     * @param meteor The meteor to be hit.
     */
    public void hitByMeteor(Meteor meteor){
        if(meteor.isCollidingWithSnake(this)){
            SoundEffectPlayer.playMusic("/audio/hit.mp3");
            if(Math.random() > 0.5) speedDown();
            removeTail();
            meteor.setRandomPosition();
            reduceScore(4 * speedController.getSpeedLevel());
        }
    }

    /**
     * If any part of the snake body collides with the gem,
     * the snake will speed up and the gem will be regenerated.
     *
     * @param gem The gem to be touched.
     */
    public void touchGem(Gem gem) {
        if (gem.isCollidingWithSnake(this)) {
            SoundEffectPlayer.playMusic("/audio/gem.mp3");
            gem.setRandomPosition();
            speedUp();
            addScore(8 * speedController.getSpeedLevel());
        }
    }

    /**
     * If any part of the snake body collides with the coin,
     * the snake will add score and the coin will be regenerated.
     *
     * @param coin The coin to be eaten.
     */
    public void eatCoin(Coin coin) {
        if (coin.isCollidingWithSnake(this)) {
            SoundEffectPlayer.playMusic("/audio/coin.mp3");
            coin.setRandomPosition();
            addScore(6 * speedController.getSpeedLevel());
        }
    }

    /**
     * Moves the snake head and body according to the current direction.
     */
    @Override
    public void move(){
        // If now is the time to move the frame
        if (speedController.isMoveFrame()){
            int direction = KeyEventHandler.getNextDirection();
            moveBody();
            // Move the snake head according to the current direction
            switch (direction) {
                case RIGHT -> snakeHead.setX(snakeHead.getX() + 1);
                case LEFT -> snakeHead.setX(snakeHead.getX() - 1);
                case UP -> snakeHead.setY(snakeHead.getY() - 1);
                case DOWN -> snakeHead.setY(snakeHead.getY() + 1);
                default -> System.out.println("Invalid direction in judgeMoveFrame: " + direction);
            }
            // Update the current direction
            KeyEventHandler.setCurrentDirection(direction);
        }
        // Update the frame
        speedController.updateFrame();
    }

    /**
     * Gets the image of the snake body.
     *
     * @return The image of the snake body.
     */
    @Override
    public Image getBodyImage() {
        return switch (level) {
            case MEDIUM -> ImageLoader.getImage("/snake/rabbit-snake/snake-body.png");
            case HARD -> ImageLoader.getImage("/snake/ghost-snake/snake-body.png");
            case HELL -> ImageLoader.getImage("/snake/space-snake/snake-body.png");
            default -> ImageLoader.getImage("/snake/plain-snake/snake-body.png");
        };
    }

    /**
     * Gets the image of the snake head.
     *
     * @param direction The direction of the snake head.
     * @return The image of the snake head.
     */
    @Override
    public Image getHeadImage(int direction) {
        StringBuilder imagePathBuilder = new StringBuilder("/snake/");

        switch (level) {
            case MEDIUM -> imagePathBuilder.append("rabbit-snake/");
            case HARD -> imagePathBuilder.append("ghost-snake/");
            case HELL -> imagePathBuilder.append("space-snake/");
            default -> imagePathBuilder.append("plain-snake/");
        }

        switch (direction) {
            case RIGHT -> imagePathBuilder.append("snake-head-right.png");
            case LEFT -> imagePathBuilder.append("snake-head-left.png");
            case UP -> imagePathBuilder.append("snake-head-up.png");
            case DOWN -> imagePathBuilder.append("snake-head-down.png");
            default -> System.out.println("Invalid direction in getHeadImage: " + direction);
        }

        return ImageLoader.getImage(imagePathBuilder.toString());
    }

    /**
     * Sets the level(easy, medium, hard, hell) of the snake.
     *
     * @param level The level of the snake.
     */
    public void setLevel(int level){
        this.level = level;
        speedUp(level);
    }



}
