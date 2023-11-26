package com.megasnake.model.game;


import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.MusicPlayer;
import com.megasnake.controller.game.SpeedController;
import com.megasnake.utils.KeyEventHandler;
import javafx.scene.image.Image;

import java.util.ArrayList;

import static com.megasnake.controller.game.SnakeGameController.ROWS;

public class Snake extends SnakeBase{

    private int level;

    public Snake() {
        snakeBody = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);
        speedController = new SpeedController();
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

    public void eatCoin(Coin coin) {
        if (coin.isCollidingWithSnake(this)) {
            MusicPlayer.playMusic("/audio/coin.mp3");
            coin.setRandomPosition();
            score += 6 * speedController.getSpeedLevel();
        }
    }

    public void judgeMoveFrame(int direction){
        if (speedController.isMoveFrame()){
            moveBody();
            switch (direction) {
                case KeyEventHandler.RIGHT -> {
                    snakeHead.x++;
                    KeyEventHandler.setCurrentDirection(KeyEventHandler.RIGHT);
                }
                case KeyEventHandler.LEFT -> {
                    snakeHead.x--;
                    KeyEventHandler.setCurrentDirection(KeyEventHandler.LEFT);
                }
                case KeyEventHandler.UP -> {
                    snakeHead.y--;
                    KeyEventHandler.setCurrentDirection(KeyEventHandler.UP);
                }
                case KeyEventHandler.DOWN -> {
                    snakeHead.y++;
                    KeyEventHandler.setCurrentDirection(KeyEventHandler.DOWN);
                }
                default -> System.out.println("Invalid direction in judgeMoveFrame: " + direction);
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
            default -> System.out.println("Invalid direction in move: " + KeyEventHandler.getNextDirection());
        }
    }
    @Override
    public Image getBodyImage() {
        return switch (level) {
            case 1 -> ImageLoader.getImage("/snake/rabbit-snake/snake-body.png");
            case 2 -> ImageLoader.getImage("/snake/ghost-snake/snake-body.png");
            case 3 -> ImageLoader.getImage("/snake/space-snake/snake-body.png");
            default -> ImageLoader.getImage("/snake/plain-snake/snake-body.png");
        };

    }

    @Override
    public Image getHeadImage(int direction) {
        StringBuilder imagePathBuilder = new StringBuilder("/snake/");

        switch (level) {
            case 1 -> imagePathBuilder.append("rabbit-snake/");
            case 2 -> imagePathBuilder.append("ghost-snake/");
            case 3 -> imagePathBuilder.append("space-snake/");
            default -> imagePathBuilder.append("plain-snake/");
        }

        switch (direction) {
            case KeyEventHandler.RIGHT -> imagePathBuilder.append("snake-head-right.png");
            case KeyEventHandler.LEFT -> imagePathBuilder.append("snake-head-left.png");
            case KeyEventHandler.UP -> imagePathBuilder.append("snake-head-up.png");
            case KeyEventHandler.DOWN -> imagePathBuilder.append("snake-head-down.png");
            default -> System.out.println("Invalid direction in getHeadImage: " + direction);
        }

        return ImageLoader.getImage(imagePathBuilder.toString());
    }

    public void setLevel(int level){
        this.level = level;
        speedUp(level);
    }



}
