package com.megasnake.game.controller;

import com.megasnake.game.audio.MusicPlayer;
import com.megasnake.game.model.Food;
import com.megasnake.game.model.Snake;
import com.megasnake.game.view.GameView;
import com.megasnake.game.utils.DirectionHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.PriorityQueue;

import static com.megasnake.game.utils.DirectionHandler.RIGHT;

public class SnakeGameController {
    Stage menuStage;
    Stage gameStage;
    Group root;
    Canvas canvas;
    Scene scene;
    GameView gameview;
    Timeline gameTimer;
    GraphicsContext gc;
    Food food;
    DirectionHandler directionHandler;
    Snake mySnake;
    private PriorityQueue<Integer> scoreTable;

    public static final int WIDTH = 720;
    public static final int HEIGHT = WIDTH;
    public static final int ROWS = 24;
    public static final int COLUMNS = ROWS;
    public static final int SQUARE_SIZE = WIDTH / ROWS;

    private boolean gameOver = false;

    public SnakeGameController(){
        initializeGame();
    }
    public void runSnakeGame(Stage menuStage, PriorityQueue<Integer> scoreTable, int difficulty){
        for(int i = 0; i < difficulty; i++){
            mySnake.speedUp();
        }
        this.menuStage = menuStage;
        this.scoreTable = scoreTable;
        this.menuStage.hide();
        gameStage.show();
        run();
    }

    private void run() {
        scene.setOnKeyPressed(directionHandler);
        MusicPlayer.playMusic("/frogger.mp3");
        food.generateFood(mySnake);
        gameTimer = new Timeline(new KeyFrame(Duration.millis(32), e -> mainLogic(mySnake, food)));
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    private void initializeGame(){
        gameStage = new Stage();
        gameStage.setTitle("MegaSnake");
        gameStage.getIcons().add(new Image("/snake-logo.png"));
        root = new Group();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        scene = new Scene(root);


        gameview = new GameView();
        food = new Food();
        mySnake = new Snake();
        directionHandler = new DirectionHandler(RIGHT);

        gameStage.setScene(scene);
    }

    private void mainLogic(Snake mySnake, Food food){
        if (gameOver) {
            gameview.drawGameOver(gc);
            scoreTable.add(mySnake.getScore());
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
        mySnake.move();

        gameview.drawAll(gc, mySnake, food);

        gameOver(mySnake);
        mySnake.eatFood(food);
    }

    private void gameOver(Snake mySnake) {
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
