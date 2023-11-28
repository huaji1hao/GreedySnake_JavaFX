package com.megasnake;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ViewManager manager = new ViewManager();
        Stage menuStage = manager.getMainStage();

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