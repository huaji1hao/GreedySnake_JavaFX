package com.megasnake.game.model;

import com.megasnake.game.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.game.controller.SnakeGameController.COLUMNS;
import static com.megasnake.game.controller.SnakeGameController.ROWS;

public class Gem extends SnakeObject{
    private final String[] gemImages = new String[]{"/stone/green-gem.png", "/stone/purple-gem.png"};

    public Gem(){
        setNewGemImage();
        speedController = new SpeedController();
        speedController.speedDown();
        speedController.speedDown();
        speedController.speedDown();
        setRandomPosition();
    }

    private void setNewGemImage(){
        try{
            image = new Image(getClass().getResource(gemImages[new Random().nextInt(gemImages.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    @Override
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt(ROWS);
        setNewGemImage();
        if(Math.random() > 0.9) speedController.speedUp();
    }
}
