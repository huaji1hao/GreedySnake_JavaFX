package com.megasnake.ui.controller;


import com.megasnake.game.audio.BackgroundMusicPlayer;
import com.megasnake.ui.view.SnakeButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ButtonController {
    List<SnakeButton> menuButtons;
    AnchorPane mainPane;

    Stage mainStage;

    SubSceneController subSceneController;


    private final static int MENU_BUTTONS_START_X = 60;
    private final static int MENU_BUTTONS_START_Y = 150;

    public ButtonController(Stage mainStage, AnchorPane mainPane, SubSceneController subSceneController){
        this.mainPane = mainPane;
        this.mainStage = mainStage;
        this.subSceneController = subSceneController;
        menuButtons = new ArrayList<>();
    }

    public void createButtons() {
        createPlayButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }


    private void addMenuButton(SnakeButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createPlayButton() {
        SnakeButton startButton = new SnakeButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subSceneController.showThemeChooserSubScene();
            }
        });
    }

    private void createScoresButton() {
        SnakeButton scoresButton = new SnakeButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subSceneController.drawScoreTableOnSubScene();
                subSceneController.showScoresSubScene();
            }
        });
    }

    private void createHelpButton() {
        SnakeButton helpButton = new SnakeButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subSceneController.showHelpSubScene();
            }
        });
    }

    private void createCreditsButton() {
        SnakeButton creditsButton = new SnakeButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subSceneController.showCreditsSubScene();
            }
        });
    }

    private void createExitButton() {
        SnakeButton exitButton = new SnakeButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackgroundMusicPlayer.stopMusic();
                mainStage.close();
            }
        });
    }


}
