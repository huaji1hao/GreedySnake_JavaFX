package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.ImageLoader;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

public class Gem extends SnakeObject{

    public Gem(){
        setNewGemImage();
        speedController = new SpeedController();
        speedController.speedDown();
        setRandomPosition();
        horizontalDirection = -1;
    }

    private void setNewGemImage(){
        image = ImageLoader.getImage(ImageLoader.GEM_IMAGES[random.nextInt(ImageLoader.GEM_IMAGES.length)]);
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
