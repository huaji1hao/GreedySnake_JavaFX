package com.megasnake.model.game;

import com.megasnake.controller.game.SpeedController;
import javafx.scene.image.Image;

import java.util.List;

public abstract class SnakeBase implements movable{
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

    public void moveBody(){
        for (int i = getBodySize() - 1; i >= 1; i--) {
            getBodyPart(i).x = getBodyPart(i-1).x;
            getBodyPart(i).y = getBodyPart(i-1).y;
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


}
