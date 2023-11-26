package com.megasnake.model.game;

import com.megasnake.controller.game.SnakeGameController;
import com.megasnake.controller.game.SpeedController;
import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.MusicPlayer;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.megasnake.controller.game.SnakeGameController.*;
import static com.megasnake.utils.KeyEventHandler.*;

public class AISnake extends SnakeBase {
    private int currentDirection = RIGHT;
    private final Food[] foods;

    public AISnake(Food[] foods) {
        this.foods = foods;
        speedController = new SpeedController();
        speedController.speedUp();

        Random random = new Random();
        int x = random.nextInt(COLUMNS);
        int y = random.nextInt(ROWS);

        snakeBody = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            snakeBody.add(new Point(x, y));
        }
        snakeHead = snakeBody.get(0);

    }

    @Override
    public Image getHeadImage(int direction) {
        return switch (direction) {
            case LEFT -> ImageLoader.getImage("/snake/ninja-snake/snake-head-left.png");
            case UP -> ImageLoader.getImage("/snake/ninja-snake/snake-head-up.png");
            case DOWN -> ImageLoader.getImage("/snake/ninja-snake/snake-head-down.png");
            default -> ImageLoader.getImage("/snake/ninja-snake/snake-head-right.png");
        };
    }

    @Override
    public Image getBodyImage(){
        return ImageLoader.getImage("/snake/ninja-snake/snake-body.png");
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public void move() {
        if(speedController.isMoveFrame()){
            if (foods == null || foods.length == 0) {
                return;
            }

            Food nearestFood = findNearestFood();
            if (nearestFood == null) {
                return;
            }
            moveBody();

            // 计算移动方向
            int deltaX = nearestFood.getX() - snakeHead.getX();
            int deltaY = nearestFood.getY() - snakeHead.getY();

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                snakeHead.setX(snakeHead.getX() + Integer.signum(deltaX));
                currentDirection = deltaX > 0 ? RIGHT : LEFT;
            } else {
                snakeHead.setY(snakeHead.getY() + Integer.signum(deltaY));
                currentDirection = deltaY > 0 ? DOWN : UP;
            }

            // 确保 AI 不会移动出界
            snakeHead.setX(Math.max(0, Math.min(snakeHead.getX(), COLUMNS - 1)));
            snakeHead.setY(Math.max(0, Math.min(snakeHead.getY(), SnakeGameController.ROWS - 1)));
        }
        speedController.updateFrame();


    }

    private Food findNearestFood() {
        return Arrays.stream(foods)
                .min((food1, food2) -> {
                    int dist1 = distance(snakeHead.getX(), snakeHead.getY(), food1.getX(), food1.getY());
                    int dist2 = distance(snakeHead.getX(), snakeHead.getY(), food2.getX(), food2.getY());
                    return Integer.compare(dist1, dist2);
                })
                .orElse(null);
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public void eatFood(Food food, Snake snake) {
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            if(Math.random()>0.7){
                snakeBody.add(new Point(snakeBody.get(snakeBody.size()-1).getX(), snakeBody.get(snakeBody.size()-1).getY()));
            }
            MusicPlayer.playMusic("/audio/laugh.mp3");
            food.generateFood(snake);
            score += 5 * speedController.getSpeedLevel();
        }
    }
}
