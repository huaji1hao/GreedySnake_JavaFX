package com.megasnake.game.model;


import com.megasnake.game.controller.SpeedController;
import com.megasnake.game.utils.DirectionHandler;

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
            snakeBody.add(new Point(snakeBody.get(snakeBody.size()-1).x, snakeBody.get(snakeBody.size()-1).y));
            food.generateFood(this);
            score += 5 * speedController.getSpeedLevel();
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
            if(direction == DirectionHandler.RIGHT){
                snakeHead.x++;
                DirectionHandler.setCurrentDirection(DirectionHandler.RIGHT);
            }
            else if(direction == DirectionHandler.LEFT){
                snakeHead.x--;
                DirectionHandler.setCurrentDirection(DirectionHandler.LEFT);
            }
            else if(direction == DirectionHandler.UP){
                snakeHead.y--;
                DirectionHandler.setCurrentDirection(DirectionHandler.UP);
            }
            else if(direction == DirectionHandler.DOWN){
                snakeHead.y++;
                DirectionHandler.setCurrentDirection(DirectionHandler.DOWN);
            }
        }
        speedController.updateFrame();
    }


    public void move(){
        switch (DirectionHandler.getNextDirection()) {
            case DirectionHandler.RIGHT -> judgeMoveFrame(DirectionHandler.RIGHT);
            case DirectionHandler.LEFT -> judgeMoveFrame(DirectionHandler.LEFT);
            case DirectionHandler.UP -> judgeMoveFrame(DirectionHandler.UP);
            case DirectionHandler.DOWN -> judgeMoveFrame(DirectionHandler.DOWN);
        }
    }

}