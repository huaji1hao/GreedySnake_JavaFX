package com.megasnake.game.model;


import com.megasnake.audio.MusicPlayer;
import com.megasnake.game.controller.SpeedController;
import com.megasnake.game.utils.KeyEventHandler;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

import static com.megasnake.game.controller.SnakeGameController.ROWS;

public class Snake  implements movable{
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private SpeedController speedController;
    private int score = 0;

    public Snake() {
        for (int i = 0; i < 4; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        speedController = new SpeedController();
    }

    public void speedUp() {
        speedController.speedUp();
    }

    public void speedDown() {
        speedController.speedDown();
    }

    public double getMoveFrame() {
        return speedController.getFrameRate();
    }

    public int getBodySize() {
        return snakeBody.size();
    }

    public Point getBodyPart(int index) {
        return snakeBody.get(index);
    }

    public Point getSnakeHead() {
        return snakeHead;
    }

    public void eatFood(Food food) {
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            MusicPlayer.playMusic("/audio/eat.mp3");
            snakeBody.add(new Point(snakeBody.get(snakeBody.size()-1).x, snakeBody.get(snakeBody.size()-1).y));
            food.generateFood(this);
            score += 5 * speedController.getSpeedLevel();
        }
    }

    public void hitByMeteor(Meteor meteor){
        if(meteor.isCollidingWithSnake(this)){
            MusicPlayer.playMusic("/audio/hit.mp3");
            if(Math.random() > 0.5)speedDown();
            if(snakeBody.size() > 1 )snakeBody.remove(snakeBody.size()-1);
            meteor.setRandomPosition();
            score -= 4 * speedController.getSpeedLevel();
        }
    }

    public void touchGem(Gem gem) {
        if (gem.isCollidingWithSnake(this)) {
            MusicPlayer.playMusic("/audio/gem.mp3");
            gem.setRandomPosition();
            speedUp();
            score += 8 * speedController.getSpeedLevel();
        }
    }

    public int getScore() {
        return score;
    }

    public void moveBody(){
        for (int i = getBodySize() - 1; i >= 1; i--) {
            getBodyPart(i).x = getBodyPart(i-1).x;
            getBodyPart(i).y = getBodyPart(i-1).y;
        }
    }

    public void judgeMoveFrame(int direction){
        if (speedController.isMoveFrame()){
            moveBody();
            if(direction == KeyEventHandler.RIGHT){
                snakeHead.x++;
                KeyEventHandler.setCurrentDirection(KeyEventHandler.RIGHT);
            }
            else if(direction == KeyEventHandler.LEFT){
                snakeHead.x--;
                KeyEventHandler.setCurrentDirection(KeyEventHandler.LEFT);
            }
            else if(direction == KeyEventHandler.UP){
                snakeHead.y--;
                KeyEventHandler.setCurrentDirection(KeyEventHandler.UP);
            }
            else if(direction == KeyEventHandler.DOWN){
                snakeHead.y++;
                KeyEventHandler.setCurrentDirection(KeyEventHandler.DOWN);
            }
        }
        speedController.updateFrame();
    }


    public void move(){
        switch (KeyEventHandler.getNextDirection()) {
            case KeyEventHandler.RIGHT -> judgeMoveFrame(KeyEventHandler.RIGHT);
            case KeyEventHandler.LEFT -> judgeMoveFrame(KeyEventHandler.LEFT);
            case KeyEventHandler.UP -> judgeMoveFrame(KeyEventHandler.UP);
            case KeyEventHandler.DOWN -> judgeMoveFrame(KeyEventHandler.DOWN);
        }
    }


}
