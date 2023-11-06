package com.megasnake.model;


import java.awt.*;
import java.util.ArrayList;

import java.util.List;

import static com.megasnake.controller.GameLogic.*;

public class Snake  {
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private int score = 0;

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
            insertBodyPart(new Point(-1, -1));
            food.generateFood(this);
            score += 5;
        }
    }

    public int getScore() {
        return score;
    }

    public void moveRight() {
        snakeHead.x++;
    }

    public void moveLeft() {
        snakeHead.x--;
    }

    public void moveUp() {
        snakeHead.y--;
    }

    public void moveDown() {
        snakeHead.y++;
    }

}
