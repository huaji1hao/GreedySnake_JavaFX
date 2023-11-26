package com.megasnake.model.game;

import com.megasnake.controller.game.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.game.SnakeGameController.COLUMNS;
import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Gem extends SnakeObject{
    private final String[] gemImages = new String[]{"/stone/green-gem.png", "/stone/purple-gem.png"};

    public Gem(){
        setNewGemImage();
        speedController = new SpeedController();
        speedController.speedDown();
        setRandomPosition();
        horizontalDirection = -1;
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
    @Override
    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;

            this.x += horizontalDirection;
            if (this.x >= COLUMNS - 1 || this.x <= 0) {
                horizontalDirection *= -1;
            }

            this.rotationAngle += 10;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }

}
