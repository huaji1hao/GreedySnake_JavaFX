package com.megasnake;

import com.megasnake.ui.controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage menuStage) throws Exception {
        ViewController controller = new ViewController();
        menuStage = controller.getMainStage();
        menuStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}