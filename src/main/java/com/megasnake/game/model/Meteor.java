package com.megasnake.game.model;

import com.megasnake.game.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

public class Meteor extends SnakeObject{
    private String[] meteorImages = new String[]{"/meteor_grey.png", "/meteor_brown.png"};

    public Meteor(){
        setNewMeteorImage();
        speedController = new SpeedController();
    }

    private void setNewMeteorImage(){
        try{
            image = new Image(getClass().getResource(meteorImages[new Random().nextInt(meteorImages.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    public void setRandomPosition(){
        super.setRandomPosition();
        setNewMeteorImage();
    }

}
