package com.megasnake.ui.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ViewController {
    private  static final int WIDTH = 1024;
    private  static final int HEIGHT = 700;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    ButtonController buttonController;
    SubSceneController subSceneController;
    public ViewController() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("MegaSnake");
        mainStage.getIcons().add(new Image("/snake-logo2.png"));

        subSceneController = new SubSceneController(mainStage, mainPane);
        subSceneController.createSubScenes();

        buttonController = new ButtonController(mainStage, mainPane, subSceneController);
        buttonController.createButtons();

        createBackground();
//        createLogo();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createBackground(){
//        Image backgroundImage = new Image("purple.png", 256, 256, false, true);
//        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        Image backgroundImage = new Image("jungle2.png", 1024, 700, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("logo.jpg");
        logo.setLayoutX(400);
        logo.setLayoutY(20);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new Glow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }

}
