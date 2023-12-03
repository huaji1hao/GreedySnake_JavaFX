package com.megasnake.controller;

import com.megasnake.view.game.GameView;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Abstract class for the game controllers.
 *
 * @Author Junfeng ZHU
 */
public abstract class GameController {
    protected final Stage menuStage;
    protected Stage gameStage;
    protected Group root;
    protected GraphicsContext gc;

    protected Scene scene;
    protected GameView gameview;
    protected Timeline gameTimer;
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int ROWS = 20;
    public static final int COLUMNS = ROWS;
    public static final int SQUARE_SIZE = WIDTH / ROWS;

    protected boolean isGameOver = false;

    /**
     * Constructor for the game controller.
     *
     * @param menuStage The stage which will be hidden when the game starts.
     */
    protected GameController(Stage menuStage) {
        this.menuStage = menuStage;

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        gameStage = new Stage();
        gameStage.setScene(scene);

        gc = canvas.getGraphicsContext2D();
        gameview = new GameView(gc);
    }

    /**
     * Starts the game.
     */
    protected abstract void run();

    /**
     * Stops the game.
     */
    protected abstract void gameOver();

    /**
     * Displays a warning message in a pop-up window.
     *
     * @param message The message to be displayed.
     */
    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
