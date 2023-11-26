package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.controller.SnakeGameController.COLUMNS;
import static com.megasnake.controller.SnakeGameController.ROWS;

public abstract class SnakeObject implements Movable {
    protected int x;
    protected int y;
    protected double rotationAngle = 0;
    protected Image image;
    protected SpeedController speedController;
    protected Random random = new Random();
    protected int horizontalDirection = 0;

    public Image getImage(){
        return image;
    }

    public void setRandomPosition(){
        this.x = random.nextInt(COLUMNS);
        this.y = -random.nextInt((int) (ROWS * 0.5));
    }

    public int getHorizontalDirection() {
        return horizontalDirection;
    }

    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;
            this.rotationAngle += 10;
            checkIfIsOutOfScreen();
        }
        speedController.updateFrame();
    }

    public double getRotationAngle(){
        return this.rotationAngle;
    }

    public void checkIfIsOutOfScreen(){
        if(this.y > ROWS){
            this.setRandomPosition();
        }
    }

    public boolean isCollidingWithSnake(Snake snake){
        for(int i = 0; i < snake.getBodySize(); i++){
            if(snake.getBodyPart(i).getX() == this.x && snake.getBodyPart(i).getY() == this.y){
                return true;
            }
        }
        return false;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


    public double getMoveFrame() {
        return speedController.getFrameRate();
    }
}
