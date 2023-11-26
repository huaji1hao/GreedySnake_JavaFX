package com.megasnake.controller.ui;


import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.model.component.SnakeButton;
import com.megasnake.model.component.SoundButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ButtonController {
    List<SnakeButton> menuButtons;
    AnchorPane mainPane;

    Stage mainStage;

    SubSceneController subSceneController;


    private static final int MENU_BUTTONS_START_X = 60;
    private static final int MENU_BUTTONS_START_Y = 150;

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
        createSoundButton();
        createExitButton();
    }

    private void addMenuButton(SnakeButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100.0);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createSoundButton() {
        SoundButton soundButton = new SoundButton();
        soundButton.setLayoutX(900);
        soundButton.setLayoutY(20);
        mainPane.getChildren().add(soundButton);
        soundButton.setOnAction(event -> soundButton.changeButtonStyleAndOperateMusic());
    }

    private void createPlayButton() {
        SnakeButton startButton = new SnakeButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(event -> subSceneController.showThemeChooserSubScene());
    }

    private void createScoresButton() {
        SnakeButton scoresButton = new SnakeButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(event -> {
            subSceneController.drawScoreTableOnSubScene();
            subSceneController.showScoresSubScene();
        });
    }

    private void createHelpButton() {
        SnakeButton helpButton = new SnakeButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(event -> subSceneController.showHelpSubScene());
    }

    private void createCreditsButton() {
        SnakeButton settingButton = new SnakeButton("Settings");
        addMenuButton(settingButton);

        settingButton.setOnAction(event -> {
            subSceneController.drawSettingSubScene();
            subSceneController.showSettingSubScene();
        });
    }

    private void createExitButton() {
        SnakeButton exitButton = new SnakeButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(event -> {
            BackgroundMusicPlayer.stopMusic();
            mainStage.close();
        });
    }


}
