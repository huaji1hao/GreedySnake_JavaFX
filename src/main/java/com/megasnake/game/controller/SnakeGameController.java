package com.megasnake.game.controller;

import com.megasnake.audio.BackgroundMusicPlayer;
import com.megasnake.audio.MusicPlayer;
import com.megasnake.game.model.Food;
import com.megasnake.game.model.Snake;
import com.megasnake.game.view.GameView;
import com.megasnake.game.utils.KeyEventHandler;
import com.megasnake.ui.component.SnakeButton;
import com.megasnake.ui.component.SnakeSubScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.awt.*;
import java.util.PriorityQueue;

import static com.megasnake.game.utils.KeyEventHandler.RIGHT;

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
    KeyEventHandler keyEventHandler;
    Snake mySnake;
    int difficulty;
    private PriorityQueue<Integer> scoreTable;

    public static final int WIDTH = 720;
    public static final int HEIGHT = WIDTH;
    public static final int ROWS = 20;
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
        this.difficulty = difficulty;
        this.menuStage = menuStage;
        this.scoreTable = scoreTable;
        this.menuStage.hide();
        gameStage.show();
        run();
    }

    private void run() {
        BackgroundMusicPlayer.repeatMusic("/audio/frogger.mp3");
        food.generateFood(mySnake);
        scene.setOnKeyPressed(keyEventHandler);
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    private void initializeGame(){
        gameStage = new Stage();
        gameStage.setTitle("MegaSnake");
        gameStage.getIcons().add(new Image("/snake-logo.png"));
        gameStage.setOnCloseRequest((WindowEvent we) -> {
            BackgroundMusicPlayer.stopMusic();
            Platform.exit();
            System.exit(0);
        });

        root = new Group();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        scene = new Scene(root);

        food = new Food();
        mySnake = new Snake();

        gameTimer = new Timeline(new KeyFrame(Duration.millis(32), e -> mainLogic(mySnake, food)));
        keyEventHandler = new KeyEventHandler(RIGHT, gameTimer, gc);
        gameview = new GameView();

        gameStage.setScene(scene);
    }

    private void mainLogic(Snake mySnake, Food food){
        if (gameOver) {
            afterGameOver();
            return;
        }
        mySnake.move();

        gameview.drawAll(gc, mySnake, food, difficulty);

        isGameOver(mySnake);
        mySnake.eatFood(food);
    }

    private void isGameOver(Snake mySnake) {
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

    private void afterGameOver(){
        gameTimer.stop();
        BackgroundMusicPlayer.stopMusic();
        gameview.drawGameOver(gc);
        MusicPlayer.playMusic("/audio/game-over.mp3");

        scoreTable.add(mySnake.getScore());

        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        pause.setOnFinished(event -> {
            // create a new scene when the game is over
            SnakeSubScene backSubScene = new SnakeSubScene();
            root.getChildren().add(backSubScene);
            backSubScene.moveSubSceneInGame();
            backSubScene.getPane().getChildren().add(createButtonToBack());
        });

        pause.play();


    }

    private SnakeButton createButtonToBack(){
        SnakeButton backButton = new SnakeButton("Go Back", 1);
        backButton.setLayoutX(350);
        backButton.setLayoutY(280);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameStage.close();
                menuStage.show();
                BackgroundMusicPlayer.repeatMusic("/audio/ui-background.mp3");
            }
        });


        return backButton;
    }




}
