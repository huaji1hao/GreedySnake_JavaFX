package com.megasnake.model.game;

import com.megasnake.controller.game.SpeedController;
import com.megasnake.utils.ImageLoader;

public class Meteor extends SnakeObject{

    public Meteor(){
        speedController = new SpeedController();
        setNewMeteorImage();
        setRandomPosition();
    }

    private void setNewMeteorImage(){
        image = ImageLoader.getImage(ImageLoader.METEOR_IMAGES[random.nextInt(ImageLoader.METEOR_IMAGES.length)]);
    }

    @Override
    public void setRandomPosition(){
        super.setRandomPosition();
        setNewMeteorImage();
        if(Math.random() > 0.8) speedController.speedUp();
    }

}
