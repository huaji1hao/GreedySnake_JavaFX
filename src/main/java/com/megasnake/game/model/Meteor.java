package com.megasnake.game.model;

import com.megasnake.game.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

public class Meteor extends SnakeObject{
    private final String[] meteorImages = new String[]{"/stone/yellow.png", "/stone/orange.png", "/stone/blue.png"};

    public Meteor(){
        speedController = new SpeedController();
        setNewMeteorImage();
        setRandomPosition();
    }

    private void setNewMeteorImage(){
        try{
            image = new Image(getClass().getResource(meteorImages[new Random().nextInt(meteorImages.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    @Override
    public void setRandomPosition(){
        super.setRandomPosition();
        setNewMeteorImage();
        if(Math.random() > 0.8) speedController.speedUp();
    }

}
