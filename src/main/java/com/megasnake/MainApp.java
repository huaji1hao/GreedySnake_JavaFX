package com.megasnake;

import com.megasnake.game.audio.BackgroundMusicPlayer;
import com.megasnake.ui.controller.ViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage menuStage) throws Exception {
        ViewController controller = new ViewController();
        menuStage = controller.getMainStage();

        menuStage.setOnCloseRequest((WindowEvent we) -> {
            BackgroundMusicPlayer.stopMusic();
            Platform.exit();
            System.exit(0);
        });

        menuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}