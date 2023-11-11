package com.megasnake.game.controller;

import com.megasnake.game.model.Food;
import com.megasnake.game.model.Snake;
import com.megasnake.game.view.GameView;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

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
        mySnake.move();

        gameview.drawAll(gc, mySnake, food);

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
