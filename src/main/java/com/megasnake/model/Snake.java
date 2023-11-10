package com.megasnake.model;


import com.megasnake.controller.SpeedController;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

import static com.megasnake.controller.GameLogic.*;
import static com.megasnake.util.DirectionHandler.*;

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
        speedController = new SpeedController(5);
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
            score += 5;
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
            if(direction == RIGHT){
                snakeHead.x++;
                setCurrentDirection(RIGHT);
            }
            else if(direction == LEFT){
                snakeHead.x--;
                setCurrentDirection(LEFT);
            }
            else if(direction == UP){
                snakeHead.y--;
                setCurrentDirection(UP);
            }
            else if(direction == DOWN){
                snakeHead.y++;
                setCurrentDirection(DOWN);
            }
        }
        speedController.updateFrame();
    }


    public void move(){
        switch (getNextDirection()) {
            case RIGHT -> judgeMoveFrame(RIGHT);
            case LEFT -> judgeMoveFrame(LEFT);
            case UP -> judgeMoveFrame(UP);
            case DOWN -> judgeMoveFrame(DOWN);
        }
    }

}
