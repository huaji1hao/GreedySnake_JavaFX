package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Abstract class for the snake.
 *
 * @Author Junfeng ZHU
 */
public abstract class Snake implements Movable {
    protected SpeedController speedController;
    protected int score = 0;
    protected List<Point> snakeBody;
    protected Point snakeHead;

    /**
     * Gets the move frame of the snake (the frame rate in which the snake moves).
     *
     * @return The move frame of the snake.
     */
    public double getMoveFrame() {
        return speedController.getFrameRate();
    }

    /**
     * Gets the size of the snake's body.
     *
     * @return The size of the snake's body.
     */
    public int getBodySize() {
        return snakeBody.size();
    }

    /**
     * Gets the body part of the snake at the specified index.
     *
     * @param index The index of the body part.
     * @return The body part of the snake at the specified index.
     */
    public Point getBodyPart(int index) {
        return snakeBody.get(index);
    }

    /**
     * Gets the head of the snake.
     *
     * @return The head of the snake.
     */
    public Point getSnakeHead() {
        return snakeHead;
    }

    /**
     * Removes the last element of the snake's body if it's size is greater than 1.
     */
    public void removeTail(){
        if(snakeBody.size() > 1 )snakeBody.remove(snakeBody.size()-1);
    }

    /**
     * Moves the snake's body.
     */
    public void moveBody(){
        for (int i = getBodySize() - 1; i >= 1; i--) {
            getBodyPart(i).setX(getBodyPart(i-1).getX());
            getBodyPart(i).setY(getBodyPart(i-1).getY());
        }
    }

    /**
     * Gets the image of the snake's head.
     *
     * @param direction The direction of the snake's head.
     * @return The image of the snake's head.
     */
    public abstract Image getHeadImage(int direction);

    /**
     * Gets the image of the snake's body.
     *
     * @return The image of the snake's body.
     */
    public abstract Image getBodyImage();

    /**
     * Gets the score of the snake.
     *
     * @return The score of the snake.
     */
    public int getScore() {
        return score;
    }

    /**
     * Speeds up the snake by one level.
     */
    public void speedUp() {
        speedController.speedUp();
    }
    /**
     * Speeds up the snake by the specified level.
     *
     * @param level The level to speed up the snake.
     */
    public void speedUp(int level) {
        speedController.speedUp(level);
    }

    /**
     * Slows down the snake by one level.
     */
    public void speedDown() {
        speedController.speedDown();
    }

    /**
     * Reduces the score of the snake by the specified amount.
     */
    public void reduceScore(int score){
        this.score -= score;
        if(this.score < 0) this.score = 0;
    }

    /**
     * Adds the specified amount to the score of the snake.
     */
    public void addScore(int score){
        this.score += score;
    }


}
