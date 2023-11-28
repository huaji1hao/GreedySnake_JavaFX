package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import javafx.scene.image.Image;

import java.util.List;

public abstract class SnakeBase implements Movable {
    protected SpeedController speedController;
    protected int score = 0;
    protected List<Point> snakeBody;
    protected Point snakeHead;

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

    public void removeTail(){
        if(snakeBody.size() > 1 )snakeBody.remove(snakeBody.size()-1);
    }

    public void moveBody(){
        for (int i = getBodySize() - 1; i >= 1; i--) {
            getBodyPart(i).setX(getBodyPart(i-1).getX());
            getBodyPart(i).setY(getBodyPart(i-1).getY());
        }
    }

    public abstract Image getHeadImage(int direction);

    public abstract Image getBodyImage();

    public int getScore() {
        return score;
    }

    public void speedUp() {
        speedController.speedUp();
    }
    public void speedUp(int level) {
        speedController.speedUp(level);
    }

    public void speedDown() {
        speedController.speedDown();
    }

    public void reduceScore(int score){
        this.score -= score;
        if(this.score < 0) this.score = 0;
    }

    public void addScore(int score){
        this.score += score;
    }


}
