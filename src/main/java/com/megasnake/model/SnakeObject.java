package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

/**
 * Abstract class for all movable objects (except the snake) in the game.
 *
 * @Author Junfeng ZHU
 */
public abstract class SnakeObject implements Movable {
    protected int x;
    protected int y;
    protected double rotationAngle = 0;
    protected Image image;
    protected SpeedController speedController;
    protected Random random = new Random();
    protected static final int HORIZONTAL_LEFT = -1;
    protected static final int HORIZONTAL_RIGHT = 1;
    protected static final int NO_HORIZONTAL_MOVEMENT = 0;
    protected int horizontalDirection = NO_HORIZONTAL_MOVEMENT;

    /**
     * Returns the image of the object.
     *
     * @return The image of the object.
     */
    public Image getImage(){
        return image;
    }

    /**
     * Sets the position of the object to a random position in the columns and above the screen.
     */
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt((int) (ROWS * 0.5));
    }

    /**
     * Returns the horizontal direction of the object.
     *
     * @return The horizontal direction of the object.
     */
    public int getHorizontalDirection() {
        return horizontalDirection;
    }

    /**
     * default move method for all objects
     */
    public void move(){
        // we only move the object if the speed controller says so
        if(speedController.isMoveFrame()){
            this.y++;
            this.rotationAngle += 10;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }

    /**
     * Returns the rotation angle of the object.
     *
     * @return The rotation angle of the object.
     */
    public double getRotationAngle(){
        return this.rotationAngle;
    }

    /**
     * Checks if the object is out of the screen and sets a new random position if it is.
     */
    public void checkIfIsOutOfScreen(){
        if(this.y > ROWS){
            this.setRandomPosition();
        }
    }

    /**
     * Checks if the object is colliding with the mySnake.
     *
     * @param mySnake The mySnake to check collision with.
     * @return True if the object is colliding with the mySnake, false otherwise.
     */
    public boolean isCollidingWithSnake(MySnake mySnake){
        // check if the object is colliding with any part of the mySnake
        for(int i = 0; i < mySnake.getBodySize(); i++){
            if(mySnake.getBodyPart(i).getX() == this.x && mySnake.getBodyPart(i).getY() == this.y){
                return true;
            }
        }
        return false;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    /**
     * Returns the move frame of the object.
     *
     * @return The move frame of the object.
     */
    public double getMoveFrame() {
        return speedController.getFrameRate();
    }
}
