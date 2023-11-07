package com.megasnake.controller;

import com.megasnake.model.Food;
import com.megasnake.model.Snake;
import com.megasnake.ui.GameView;
import com.megasnake.util.DirectionHandler;
import com.simplesnake.audio.MusicPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

import static com.megasnake.util.DirectionHandler.RIGHT;

public class SnakeGame {

    GameView gameview = new GameView();
    Food food = new Food();
    DirectionHandler directionHandler = new DirectionHandler(RIGHT);
    Snake mySnake = new Snake();
    public void runSnakeGame(Scene scene, GraphicsContext gc){
        scene.setOnKeyPressed(directionHandler);
//        MusicPlayer.playMusic("/frogger.mp3");
        food.generateFood(mySnake);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(45), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        GameLogic.mainLogic(mySnake, food, gc, gameview);
    }



}
