package com.megasnake.model;


import java.awt.*;
import java.util.ArrayList;

import java.util.List;

import static com.megasnake.controller.GameLogic.*;

public class Snake  {
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private int score = 0;

    private int slowspeed = 3;
    private int speedController = 0;

    public Snake() {
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);

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

    public void moveRight() {
        if (speedController == 0) {
            moveBody();
            snakeHead.x++;
        }
        speedController = (speedController + 1) % slowspeed;
    }

    public void moveLeft() {
        if (speedController == 0) {
            moveBody();
            snakeHead.x--;

        }
        speedController = (speedController + 1) % slowspeed;
    }



    public void moveUp() {
        if (speedController == 0) {
            moveBody();
            snakeHead.y--;
        }
        speedController = (speedController + 1) % slowspeed;
    }

    public void moveDown() {
        if (speedController == 0) {
            moveBody();
            snakeHead.y++;
        }
        speedController = (speedController + 1) % slowspeed;
    }

//    public void moveRight() {
//        snakeHead.x++;
//    }
//
//    public void moveLeft() {
//        snakeHead.x--;
//    }
//
//    public void moveUp() {
//        snakeHead.y--;
//    }
//
//    public void moveDown() {
//        snakeHead.y++;
//    }

}
