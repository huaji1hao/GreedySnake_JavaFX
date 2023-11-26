package com.megasnake.model.game;

import com.megasnake.controller.game.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.game.SnakeGameController.COLUMNS;
import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Coin extends SnakeObject{
    private final String[] coinImages = new String[]{"/stone/coin.png"};


    public Coin(){
        setNewCoinImage();
        speedController = new SpeedController();
        speedController.speedDown(3);
        setRandomPosition();
        horizontalDirection = 1;
    }

    private void setNewCoinImage(){
        try{
            image = new Image(getClass().getResource(coinImages[new Random().nextInt(coinImages.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    @Override
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt(ROWS);
        setNewCoinImage();
    }

    @Override
    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;

            this.x += horizontalDirection;
            if (this.x >= COLUMNS - 1 || this.x <= 0) {
                horizontalDirection *= -1;
            }

            this.rotationAngle += 5;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }


}
