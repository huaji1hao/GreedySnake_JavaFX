package com.megasnake.controller;

import com.megasnake.model.Food;
import com.megasnake.model.Snake;
import com.megasnake.ui.GameView;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

import static com.megasnake.util.DirectionHandler.*;

public class GameLogic {
    public static final int WIDTH = 720;
    public static final int HEIGHT = WIDTH;
    public static final int ROWS = 24;
    public static final int COLUMNS = ROWS;
    public static final int SQUARE_SIZE = WIDTH / ROWS;

    private static boolean gameOver = false;

    public static void mainLogic(Snake mySnake, Food food, GraphicsContext gc, GameView gameview){
        if (gameOver) {
            gameview.drawGameOver(gc);
            return;
        }

        gameview.drawAll(gc, mySnake, food);

        for (int i = mySnake.getBodySize() - 1; i >= 1; i--) {
            mySnake.getBodyPart(i).x = mySnake.getBodyPart(i-1).x;
            mySnake.getBodyPart(i).y = mySnake.getBodyPart(i-1).y;
        }

        switch (getCurrentDirection()) {
            case RIGHT:
                mySnake.moveRight();
                break;
            case LEFT:
                mySnake.moveLeft();
                break;
            case UP:
                mySnake.moveUp();
                break;
            case DOWN:
                mySnake.moveDown();
                break;
        }

        gameOver(mySnake);
        mySnake.eatFood(food);
    }

    public static void gameOver(Snake mySnake) {
        Point snakeHead = mySnake.getSnakeHead();
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < mySnake.getBodySize(); i++) {
            if (snakeHead.x == mySnake.getBodyPart(i).getX() && snakeHead.getY() == mySnake.getBodyPart(i).getY()) {
                gameOver = true;
                break;
            }
        }

    }

}
