package com.megasnake;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class for the MegaSnake game application.
 */
public class MainApp extends Application {

    /**
     * Initializes and displays the main game window.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        ViewManager manager = new ViewManager();
        Stage menuStage = manager.getMainStage();

        // Stop music when the window is closed
        menuStage.setOnCloseRequest((WindowEvent we) -> {
            BackgroundMusicPlayer.stopMusic();
            Platform.exit();
            System.exit(0);
        });

        menuStage.show();
    }

    /**
     * Launches the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
