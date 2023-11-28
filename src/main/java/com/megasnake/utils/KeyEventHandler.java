package com.megasnake.utils;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

import static com.megasnake.controller.SnakeGameController.HEIGHT;

public class KeyEventHandler implements EventHandler<KeyEvent> {
    Animation gameTimer;
    GraphicsContext gc;
    private static int currentDirection;
    private static int nextDirection;
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public KeyEventHandler(int initialDirection, Animation gameTimer, GraphicsContext gc) {
        currentDirection = initialDirection;
        nextDirection = initialDirection;
        this.gameTimer = gameTimer;
        this.gc = gc;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();

        if(code == KeyCode.SPACE) pauseGame();

        if(gameTimer.getStatus() == Animation.Status.PAUSED){
            drawPause(gc, gameTimer);
            return;
        }

        if (code == KeyCode.RIGHT || code == KeyCode.D) {
            if (currentDirection != LEFT) {
                nextDirection = RIGHT;
            }
        } else if (code == KeyCode.LEFT || code == KeyCode.A) {
            if (currentDirection != RIGHT) {
                nextDirection = LEFT;
            }
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            if (currentDirection != DOWN) {
                nextDirection = UP;
            }
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            if (currentDirection != UP) {
                nextDirection = DOWN;
            }
        }
    }

    public static void setCurrentDirection(int direction) {
        currentDirection = direction;
    }

    public static int getCurrentDirection() {
        return currentDirection;
    }

    public static int getNextDirection() {
        return nextDirection;
    }

    public void pauseGame(){
        if(gameTimer.getStatus() == Animation.Status.RUNNING){
            gameTimer.pause();
            BackgroundMusicPlayer.pauseMusic();
        }else{
            gameTimer.play();
            BackgroundMusicPlayer.playMusic();
        }
    }

    public void drawPause(GraphicsContext gc, Animation gameTimer) {
        // Check if the game is paused
        if(gameTimer.getStatus() == Animation.Status.PAUSED){
            String text = "Press space to continue the game"; // Text to display
            double x = 40; // X position for the text
            double y = HEIGHT / 2.0; // Y position for the text (centered vertically)

            double fontSize = 40; // Font size

            // Load the custom font
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), fontSize);
            // Use a default font if custom font fails to load
            gc.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize))); // Use the custom font if loaded successfully

            // Draw the text outline in black
            gc.setStroke(Color.BLACK); // Set the outline color to black
            gc.setLineWidth(2); // Set the outline width
            gc.strokeText(text, x, y); // Draw the text outline

            // Draw the filled text in white at the same position
            gc.setFill(Color.WHITE); // Set the fill color to white
            gc.fillText(text, x, y); // Draw the filled text
        }
    }



}
