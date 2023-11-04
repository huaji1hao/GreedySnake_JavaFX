package snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class MainApp extends Application {

    private static final int WIDTH = 870;
    private static final int HEIGHT = 560;
    private Canvas canvas;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle("Snakee Yipee");
        stage.setScene(scene);
        stage.show();

        // Set up the game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Game loop logic goes here
                // For example, you could call a method to update game objects and then draw them
                // updateGame();
                // drawGame(gc);
            }
        };
        timer.start();

        // Set up key event handlers
        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.setOnKeyReleased(this::handleKeyReleased);

        // Load the snake logo as the application icon
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/snake-logo.png")));
    }

    private void handleKeyPressed(KeyEvent event) {
        // Handle key pressed events
        // For example, you could update the direction of the snake based on the key pressed
    }

    private void handleKeyReleased(KeyEvent event) {
        // Handle key released events
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Additional methods for game logic and rendering would go here
    // For example, methods to update game objects, check collisions, draw the game state, etc.
}
