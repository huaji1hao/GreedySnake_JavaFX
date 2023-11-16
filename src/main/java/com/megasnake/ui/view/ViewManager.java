package com.megasnake.ui.view;

import com.megasnake.audio.BackgroundMusicPlayer;
import com.megasnake.ui.controller.ButtonController;
import com.megasnake.ui.controller.SubSceneController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;

public class ViewManager {
    private  static final int WIDTH = 1024;
    private  static final int HEIGHT = 700;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    ButtonController buttonController;
    SubSceneController subSceneController;
    public ViewManager() {
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
        BackgroundMusicPlayer.repeatMusic("/audio/ui-background.mp3");
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createBackground(){
        try{
            Image backgroundImage = new Image(getClass().getResource("/background/jungle2.png").toURI().toString(), 1024, 700, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        }catch (Exception e){
            System.out.println("Error loading background image: " + e.getMessage());
        }

    }

    private void createLogo() {
        ImageView logo = new ImageView("snake-logo.png");
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
