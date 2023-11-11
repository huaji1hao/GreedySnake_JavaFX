package com.megasnake.game.controller;

import com.megasnake.game.audio.MusicPlayer;
import com.megasnake.game.model.Food;
import com.megasnake.game.model.Snake;
import com.megasnake.game.view.GameView;
import com.megasnake.game.utils.DirectionHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

import static com.megasnake.game.utils.DirectionHandler.RIGHT;

public class SnakeGame {

    GameView gameview = new GameView();
    Food food = new Food();
    DirectionHandler directionHandler = new DirectionHandler(RIGHT);
    Snake mySnake = new Snake();
    public void runSnakeGame(Scene scene, GraphicsContext gc){
        scene.setOnKeyPressed(directionHandler);
        MusicPlayer.playMusic("/frogger.mp3");
        food.generateFood(mySnake);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(32), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        GameLogic.mainLogic(mySnake, food, gc, gameview);
    }



}
