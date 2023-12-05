package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.SoundEffectPlayer;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.megasnake.controller.GameController.*;
import static com.megasnake.utils.KeyEventHandler.*;

/**
 * Represents the AI snake.
 *
 * @see Snake
 * @author Junfeng ZHU
 */
public class AISnake extends Snake {
    private int currentDirection = RIGHT;
    private final Food[] foods;

    /**
     * Constructs an AI snake.
     *
     * @param foods The array of food.
     */
    public AISnake(Food[] foods) {
        this.foods = foods;
        speedController = new SpeedController();
        // Set the initial speed a little faster than the easy mode
        speedController.speedUp();

        // Randomly generate the snake's position
        Random random = new Random();
        int x = random.nextInt(COLUMNS);
        int y = random.nextInt(ROWS);

        // Generate the snake's body
        snakeBody = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            snakeBody.add(new Point(x, y));
        }
        // Set the snake's head
        snakeHead = snakeBody.get(0);

    }

    /**
     * Returns the image of the AI snake's head.
     *
     * @param direction The direction of the snake's head.
     * @return The image of the AI snake's head.
     */
    @Override
    public Image getHeadImage(int direction) {
        return switch (direction) {
            case LEFT -> ImageLoader.getImage("/snake/ninja-snake/snake-head-left.png");
            case UP -> ImageLoader.getImage("/snake/ninja-snake/snake-head-up.png");
            case DOWN -> ImageLoader.getImage("/snake/ninja-snake/snake-head-down.png");
            default -> ImageLoader.getImage("/snake/ninja-snake/snake-head-right.png");
        };
    }

    /**
     * Returns the image of the AI snake's body.
     *
     * @return The image of the AI snake's body.
     */
    @Override
    public Image getBodyImage(){
        return ImageLoader.getImage("/snake/ninja-snake/snake-body.png");
    }

    /**
     * Returns the direction of the AI snake.
     *
     * @return The direction of the AI snake.
     */
    public int getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Moves the AI snake to the direction of the nearest food.
     * @see #findNearestFood()
     */
    @Override
    public void move() {
        if(speedController.isMoveFrame()){
            // if there is no food, do nothing
            if (foods == null || foods.length == 0) {
                return;
            }

            // find the nearest food
            Food nearestFood = findNearestFood();
            // check if the nearest food is null
            if (nearestFood == null) return;

            moveBody();

            // calculate the distance between the snake's head and the nearest food
            int deltaX = nearestFood.getX() - snakeHead.getX();
            int deltaY = nearestFood.getY() - snakeHead.getY();

            // always move in the direction with the smaller distance
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                snakeHead.setX(snakeHead.getX() + Integer.signum(deltaX));
                currentDirection = deltaX > 0 ? RIGHT : LEFT;
            } else {
                snakeHead.setY(snakeHead.getY() + Integer.signum(deltaY));
                currentDirection = deltaY > 0 ? DOWN : UP;
            }

            // wrap the snake around the screen
            snakeHead.setX(Math.max(0, Math.min(snakeHead.getX(), COLUMNS - 1)));
            snakeHead.setY(Math.max(0, Math.min(snakeHead.getY(), ROWS - 1)));
        }
        speedController.updateFrame();
    }

    /**
     * Finds the nearest food.
     *
     * @return The nearest food.
     */
    private Food findNearestFood() {
        return Arrays.stream(foods)
                // return the food with the smallest distance
                .min((food1, food2) -> {
                    // calculate the Manhattan distance between the snake's head and the food for each food
                    int dist1 = distance(snakeHead.getX(), snakeHead.getY(), food1.getX(), food1.getY());
                    int dist2 = distance(snakeHead.getX(), snakeHead.getY(), food2.getX(), food2.getY());
                    return Integer.compare(dist1, dist2);
                })
                .orElse(null);
    }

    /**
     * Calculates the distance between two points by Manhattan distance.
     *
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return The distance between the two points.
     */
    private int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * If the AI snake is eating the food, the food will be regenerated and score will be added.
     *
     * @param food The food.
     * @param mySnake The player's snake.
     */
    public void eatFood(Food food, MySnake mySnake) {
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            if(Math.random() > 0.5){
                snakeBody.add(new Point(snakeBody.get(snakeBody.size()-1).getX(), snakeBody.get(snakeBody.size()-1).getY()));
            }
            SoundEffectPlayer.playMusic("/audio/laugh.mp3");
            food.generateFood(mySnake);
            score += 5 * speedController.getSpeedLevel();
        }
    }

    /**
     * Checks if the head of the player's snake is colliding with the body of the AI snake.
     *
     * @param mySnake The player's snake.
     */
    private boolean isAISnakeBodyCollidingWithMySnakeHead(MySnake mySnake) {
        int mySnakeHeadX = mySnake.getSnakeHead().getX();
        int mySnakeHeadY = mySnake.getSnakeHead().getY();
        for (Point aiBody : snakeBody) {
            if (mySnakeHeadX == aiBody.getX() && mySnakeHeadY == aiBody.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the head of the AI snake is colliding with the body of the player's snake.
     *
     * @param mySnake The player's snake.
     */
    private boolean isAISnakeHeadCollidingWithMySnakeBody(MySnake mySnake) {
        int aiSnakeHeadX = snakeHead.getX();
        int aiSnakeHeadY = snakeHead.getY();
        for (int i = 1; i < mySnake.getBodySize(); i++) {
            if (aiSnakeHeadX == mySnake.getBodyPart(i).getX() && aiSnakeHeadY == mySnake.getBodyPart(i).getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the AI snake is colliding with the player's snake,
     * @see #isAISnakeBodyCollidingWithMySnakeHead(MySnake)
     * @see #isAISnakeHeadCollidingWithMySnakeBody(MySnake)
     * the two snakes will lose their tails and lose points.
     *
     * @param mySnake The player's snake.
     */
    public void hitMySnake(MySnake mySnake) {
        if (isAISnakeBodyCollidingWithMySnakeHead(mySnake) || isAISnakeHeadCollidingWithMySnakeBody(mySnake)) {
            SoundEffectPlayer.playMusic("/audio/hit.mp3");
            // remove the tail of the AI snake
            if(snakeBody.size() > 2 ) removeTail();
            // remove the tail of the player's snake
            if(mySnake.getBodySize() > 3) mySnake.removeTail();
            reduceScore(speedController.getSpeedLevel());
            mySnake.reduceScore(speedController.getSpeedLevel());
        }
    }


}
