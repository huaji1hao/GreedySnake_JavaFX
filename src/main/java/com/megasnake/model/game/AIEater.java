package com.megasnake.model.game;

import com.megasnake.controller.game.SnakeGameController;
import com.megasnake.controller.game.SpeedController;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.megasnake.utils.KeyEventHandler.*;

public class AIEater extends SnakeObject {
    private final String SNAKE_RIGHT_HEAD = "/snake/ninjia-snake/snake-head-right.png";
    private final String SNAKE_LEFT_HEAD = "/snake/ninjia-snake/snake-head-left.png";
    private final String SNAKE_UP_HEAD = "/snake/ninjia-snake/snake-head-up.png";
    private final String SNAKE_DOWN_HEAD = "/snake/ninjia-snake/snake-head-down.png";
    private final String SNAKE_BODY = "/snake/ninjia-snake/snake-body.png";

    private Image imageRight;
    private Image imageLeft;
    private Image imageUp;
    private Image imageDown;
    private Image imageBody;

    private int currentDirection = RIGHT;
    private Food[] foods;

    private List<Point> eaterBody;
    private Point eaterHead;

    public AIEater(Food[] foods) {
        this.foods = foods;
        speedController = new SpeedController();
        speedController.speedUp();
        setRandomPosition();
        initializeImages();

        eaterBody = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            eaterBody.add(new Point(x, y));
        }
        eaterHead = eaterBody.get(0);

    }

    private void initializeImages(){
        try{
            imageRight = new Image(getClass().getResource(SNAKE_RIGHT_HEAD).toURI().toString());
            imageLeft = new Image(getClass().getResource(SNAKE_LEFT_HEAD).toURI().toString());
            imageUp = new Image(getClass().getResource(SNAKE_UP_HEAD).toURI().toString());
            imageDown = new Image(getClass().getResource(SNAKE_DOWN_HEAD).toURI().toString());
            imageBody = new Image(getClass().getResource(SNAKE_BODY).toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading meteor image: " + e.getMessage());
        }
    }

    public int getBodySize(){
        return eaterBody.size();
    }

    public Point getBodyPart(int index){
        return eaterBody.get(index);
    }

    public Point getEaterHead(){
        return eaterHead;
    }

    @Override
    public Image getImage() {
        return switch (currentDirection) {
            case RIGHT -> imageRight;
            case LEFT -> imageLeft;
            case UP -> imageUp;
            case DOWN -> imageDown;
            default -> null;
        };
    }

    public Image getBodyImage(){
        return imageBody;
    }

    @Override
    public int getX(){
        return eaterHead.x;
    }

    @Override
    public int getY(){
        return eaterHead.y;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public void setRandomPosition() {
        this.x = random.nextInt(SnakeGameController.COLUMNS);
        this.y = random.nextInt(SnakeGameController.ROWS);
    }

    public void moveBody(){
        for (int i = getBodySize() - 1; i >= 1; i--) {
            getBodyPart(i).x = getBodyPart(i-1).x;
            getBodyPart(i).y = getBodyPart(i-1).y;
        }
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
            int deltaX = nearestFood.getX() - eaterHead.x;
            int deltaY = nearestFood.getY() - eaterHead.y;

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                eaterHead.x += Integer.signum(deltaX);
                currentDirection = deltaX > 0 ? RIGHT : LEFT;
            } else {
                eaterHead.y += Integer.signum(deltaY);
                currentDirection = deltaY > 0 ? DOWN : UP;
            }

            // 确保 AI 不会移动出界
            eaterHead.x = Math.max(0, Math.min(eaterHead.x, SnakeGameController.COLUMNS - 1));
            eaterHead.y = Math.max(0, Math.min(eaterHead.y, SnakeGameController.ROWS - 1));
        }
        speedController.updateFrame();


    }

    private Food findNearestFood() {
        return Arrays.stream(foods)
                .min((food1, food2) -> {
                    int dist1 = distance(this.x, this.y, food1.getX(), food1.getY());
                    int dist2 = distance(this.x, this.y, food2.getX(), food2.getY());
                    return Integer.compare(dist1, dist2);
                })
                .orElse(null);
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public void eatFood(Food food, Snake snake) {
        if (eaterHead.x == food.getX() && eaterHead.y == food.getY()) {
            if(Math.random()>0.7){
                eaterBody.add(new Point(eaterBody.get(eaterBody.size()-1).x, eaterBody.get(eaterBody.size()-1).y));
            }

            food.generateFood(snake);

        }
    }
}
