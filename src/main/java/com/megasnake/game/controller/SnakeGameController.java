package com.megasnake.game.controller;

import com.megasnake.audio.BackgroundMusicPlayer;
import com.megasnake.audio.MusicPlayer;
import com.megasnake.game.model.*;
import com.megasnake.game.utils.ScoreWriter;
import com.megasnake.game.view.GameView;
import com.megasnake.game.utils.KeyEventHandler;
import com.megasnake.ui.component.CustomLabel;
import com.megasnake.ui.component.SnakeButton;
import com.megasnake.ui.component.SnakeSubScene;
import com.megasnake.ui.component.SnakeTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.awt.Point;

import static com.megasnake.game.utils.KeyEventHandler.RIGHT;

public class SnakeGameController {
    private Stage menuStage;
    private Stage gameStage;
    private Group root;
    private Scene scene;
    private GameView gameview;
    private Timeline gameTimer;
    private GraphicsContext gc;
    private SnakeTextField usernameInput;
    private Food[] foods;
    private KeyEventHandler keyEventHandler;
    private Snake mySnake;
    private int difficulty;
    Meteor meteor;
    Gem gem;
    Coin coin;

    private static int foodNum = 2;
    private static boolean isPlayableFeature = true;

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int ROWS = 20;
    public static final int COLUMNS = ROWS;
    public static final int SQUARE_SIZE = WIDTH / ROWS;

    private boolean gameOver = false;

    public SnakeGameController() {
        initializeGame();
    }

    public void runSnakeGame(Stage menuStage, int difficulty) {
        for (int i = 0; i < difficulty; i++) mySnake.speedUp();
        this.difficulty = difficulty;
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
        run();
    }

    private void run() {
        chooseBackgroundMusic();
        for (Food food : foods) food.generateFood(mySnake);
        scene.setOnKeyPressed(keyEventHandler);
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    private void chooseBackgroundMusic() {
        switch (difficulty) {
            case 1 -> BackgroundMusicPlayer.repeatMusic("/audio/quqidao.mp3");
            case 2 -> BackgroundMusicPlayer.repeatMusic("/audio/huoshandao.mp3");
            case 3 -> BackgroundMusicPlayer.repeatMusic("/audio/shendian.mp3");
            default -> BackgroundMusicPlayer.repeatMusic("/audio/frogger.mp3");
        }
    }

    public static void setFoodNum(int num){
        if(num > 0 && num < 6) foodNum = num;
    }

    private void initializeGame() {

        gameStage = new Stage();
        gameStage.setTitle("MegaSnake");
        gameStage.getIcons().add(new Image("/snake-logo.png"));
        gameStage.setOnCloseRequest((WindowEvent we) -> {
            BackgroundMusicPlayer.stopMusic();
            Platform.exit();
            System.exit(0);
        });

        root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        usernameInput = new SnakeTextField();

        foods = new Food[foodNum];
        for (int i = 0; i < foods.length; i++) foods[i] = new Food();

        mySnake = new Snake();
        meteor = new Meteor();
        gem = new Gem();
        coin = new Coin();

        gameTimer = new Timeline(new KeyFrame(Duration.millis(32), e -> mainLogic(mySnake, foods, meteor, gem, coin)));
        keyEventHandler = new KeyEventHandler(RIGHT, gameTimer, gc);
        gameview = new GameView();

        gameStage.setScene(scene);
    }

    private void mainLogic(Snake mySnake, Food[] foods, Meteor meteor, Gem gem, Coin coin) {
        if (gameOver) {
            afterGameOver();
            return;
        }

        mySnake.move();
        for (Food food : foods) mySnake.eatFood(food);

        if(isPlayableFeature){
            meteor.move();
            gem.move();
            coin.move();
            mySnake.hitByMeteor(meteor);
            mySnake.touchGem(gem);
            mySnake.eatCoin(coin);
        }

        gameview.drawAll(gc, mySnake, foods, difficulty, meteor, gem, coin);

        isGameOver(mySnake);
    }

    private void isGameOver(Snake mySnake) {
        if (mySnake.getBodySize() <= 1) {
            gameOver = true;
            return;
        }

        Point snakeHead = mySnake.getSnakeHead();
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
            return;
        }

        //destroy itself
        for (int i = 1; i < mySnake.getBodySize(); i++) {
            if (snakeHead.x == mySnake.getBodyPart(i).getX() && snakeHead.getY() == mySnake.getBodyPart(i).getY()) {
                gameOver = true;
                return;
            }
        }

    }

    private void afterGameOver() {
        gameTimer.stop();
        BackgroundMusicPlayer.stopMusic();
        gameview.drawGameOver(gc);
        MusicPlayer.playMusic("/audio/game-over.mp3");


        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        pause.setOnFinished(event -> {
            // create a new scene when the game is over
            SnakeSubScene backSubScene = new SnakeSubScene();
            root.getChildren().add(backSubScene);
            backSubScene.moveSubSceneInGame();

            CustomLabel label = new CustomLabel("Your score is " + mySnake.getScore(), 23);
            label.setPos(180, 100);
            usernameInput.setPos(150, 180);

            backSubScene.getPane().getChildren().add(label);
            backSubScene.getPane().getChildren().add(usernameInput);
            backSubScene.getPane().getChildren().add(createButtonToBack());
        });

        pause.play();

    }

    private SnakeButton createButtonToBack() {
        SnakeButton backButton = new SnakeButton("Go Back", 1);
        backButton.setLayoutX(350);
        backButton.setLayoutY(280);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameInput.getTextValue().trim();
                if (username.isEmpty()) {
                    showAlert("You did not enter a username, so this record will not be retained.");
                } else {
                    User newUser = new User(username, mySnake.getScore());
                    ScoreWriter.writeScoreToFile(newUser);
                }
                gameStage.close();
                menuStage.show();
                BackgroundMusicPlayer.repeatMusic("/audio/ui-background.mp3");
            }
        });


        return backButton;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean getIsPlayableFeature(){
        return isPlayableFeature;
    }

    public static void changeIsPlayableFeature(){
        isPlayableFeature = !isPlayableFeature;
    }


}
