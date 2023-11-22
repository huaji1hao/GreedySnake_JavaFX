package com.megasnake.game.model;

import com.megasnake.game.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.Random;

import static com.megasnake.game.controller.SnakeGameController.COLUMNS;
import static com.megasnake.game.controller.SnakeGameController.ROWS;

public class Meteor implements movable{
    private int x;
    private int y;
    private double rotationAngle;
    private Image meteorImage;
    private String[] meteorImages = new String[]{"/meteor_grey.png", "/meteor_brown.png"};
    private SpeedController speedController;

    public Meteor(){
        setNewMeteorImage();
        rotationAngle = 0;
        speedController = new SpeedController();
    }

    private void setNewMeteorImage(){
        try{
            meteorImage = new Image(getClass().getResource(meteorImages[new Random().nextInt(meteorImages.length)]).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    public Image getMeteorImage(){
        return meteorImage;
    }

    public void setRandomPosition(){
        this.x = (int) (Math.random() * COLUMNS);
        this.y = (int) (Math.random() * ROWS * 0.5) * (-1);

        if(Math.random() > 0.5) speedController.speedUp();
        setNewMeteorImage();
    }

    public void move(){
        if(speedController.isMoveFrame()){
            this.y++;
            this.rotationAngle += 10;
            checkIfMeteorIsOutOfScreen();
        }
        speedController.updateFrame();
    }

    public double getRotationAngle(){
        return this.rotationAngle;
    }

    public void checkIfMeteorIsOutOfScreen(){
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
