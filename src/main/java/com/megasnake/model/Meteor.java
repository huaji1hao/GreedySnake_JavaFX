package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.ImageLoader;

/**
 * Class representing a meteor object.
 *
 * @see SnakeObject
 * @author Junfeng ZHU
 */
public class Meteor extends SnakeObject{

    /**
     * Speed controller for the meteor.
     */
    public Meteor(){
        speedController = new SpeedController();
        setNewMeteorImage();
        setRandomPosition();
    }

    /**
     * Sets a new random meteor image.
     */
    private void setNewMeteorImage(){
        image = ImageLoader.getImage(ImageLoader.METEOR_IMAGES[random.nextInt(ImageLoader.METEOR_IMAGES.length)]);
    }

    /**
     * Sets a new random position for the meteor.
     */
    @Override
    public void setRandomPosition(){
        super.setRandomPosition();
        setNewMeteorImage();
        // Speed up the meteor in 20% of the cases when it changes position
        if(Math.random() > 0.8) speedController.speedUp();
    }

}
