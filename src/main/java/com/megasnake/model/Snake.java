package com.megasnake.model;


import com.megasnake.util.DirectionHandler;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

import static com.megasnake.controller.GameLogic.*;
import static com.megasnake.util.DirectionHandler.*;

public class Snake  {
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private int score = 0;
    private int slowspeed = 5;
    private int speedController = slowspeed - 1;

    public Snake() {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);

    }

    public double getMoveFrame() {
        return 1.0 * speedController / slowspeed;
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

    public void insertBodyPart(Point point) {
        snakeBody.add(point);
    }

    public void eatFood(Food food) {
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            insertBodyPart(new Point(snakeBody.get(snakeBody.size()-1).x, snakeBody.get(snakeBody.size()-1).y));
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

    public void canMove(int direction){
        if (speedController == slowspeed - 1) {
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
        speedController = (speedController + 1) % slowspeed;
    }


    public void move(){
        switch (getNextDirection()) {
            case RIGHT:
                canMove(RIGHT);
                break;
            case LEFT:
                canMove(LEFT);
                break;
            case UP:
                canMove(UP);
                break;
            case DOWN:
                canMove(DOWN);
                break;
        }
    }

}
