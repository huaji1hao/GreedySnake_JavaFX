package com.megasnake.model.game;

import com.megasnake.controller.game.SpeedController;
import com.megasnake.utils.ImageLoader;


import static com.megasnake.controller.game.SnakeGameController.COLUMNS;
import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Coin extends SnakeObject{
    public Coin(){
        setCoinImage();
        speedController = new SpeedController();
        speedController.speedDown(3);
        setRandomPosition();
        horizontalDirection = 1;
    }

    private void setCoinImage(){
        image = ImageLoader.getImage("/stone/coin.png");
    }

    @Override
    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt(ROWS);
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
