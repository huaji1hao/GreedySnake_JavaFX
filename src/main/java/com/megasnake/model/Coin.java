package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.ImageLoader;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

/**
 * Class representing a coin object.
 *
 * @see SnakeObject
 * @Author Junfeng ZHU
 */
public class Coin extends SnakeObject{
    /**
     * Constructor for the coin object.
     */
    public Coin(){
        setCoinImage();
        speedController = new SpeedController();
        // the coin will move slowly
        speedController.speedDown(3);
        setRandomPosition();
        horizontalDirection = HORIZONTAL_RIGHT;
    }

    /**
     * Sets the coin image.
     */
    private void setCoinImage(){
        image = ImageLoader.getImage("/stone/coin.png");
    }

    /**
     * Sets the position of the coin to a random position in the columns and above the screen.
     */
    @Override
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt(ROWS);
    }

    /**
     * Moves the coin object.
     */
    @Override
    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;

            this.x += horizontalDirection;
            // if the coin is at the edge of the screen, change direction
            if (this.x >= COLUMNS - 1 || this.x <= 0) horizontalDirection *= -1;

            // small rotation
            this.rotationAngle += 5;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }


}
