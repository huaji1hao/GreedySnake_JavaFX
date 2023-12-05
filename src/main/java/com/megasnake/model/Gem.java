package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.ImageLoader;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

/**
 * Class representing a gem object.
 *
 * @see SnakeObject
 * @author Junfeng ZHU
 */
public class Gem extends SnakeObject{
    /**
     * Constructor for the gem object.
     */
    public Gem(){
        setNewGemImage();
        speedController = new SpeedController();
        speedController.speedDown();
        setRandomPosition();
        horizontalDirection = HORIZONTAL_LEFT;
    }

    /**
     * Sets a new random image for the gem.
     */
    private void setNewGemImage(){
        image = ImageLoader.getImage(ImageLoader.GEM_IMAGES[random.nextInt(ImageLoader.GEM_IMAGES.length)]);
    }

    /**
     * Sets a new random position for the gem in the columns and above the screen.
     */
    @Override
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt(ROWS);
        setNewGemImage();
        // Speed up the gem in 10% of the cases when it changes position
        if(Math.random() > 0.9) speedController.speedUp();
    }
    /**
     * Moves the gem object.
     */
    @Override
    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;

            // Move the gem horizontally
            this.x += horizontalDirection;
            // Change the horizontal direction if the gem reaches the edge of the screen
            if (this.x >= COLUMNS - 1 || this.x <= 0) horizontalDirection *= -1;

            this.rotationAngle += 10;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }

}
