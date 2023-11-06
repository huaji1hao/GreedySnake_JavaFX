package com.megasnake;

import com.megasnake.controller.GameLogic;
import com.megasnake.controller.SnakeGame;
import com.megasnake.model.Food;
import com.megasnake.model.Snake;
import com.megasnake.ui.GameView;
import com.megasnake.util.DirectionHandler;
import com.simplesnake.audio.MusicPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.megasnake.controller.GameLogic.*;

public class MainApp extends Application {
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MegaSnake");
        primaryStage.getIcons().add(new Image("/snake-logo.png"));
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        SnakeGame snakeGame = new SnakeGame();
        snakeGame.runSnakeGame(scene, gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}