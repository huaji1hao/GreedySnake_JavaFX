package com.megasnake.view.ui;


import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.view.component.SnakeButton;
import com.megasnake.view.component.SnakeSwitchButton;
import com.megasnake.view.component.SoundButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ButtonManager {
    List<SnakeButton> menuButtons;
    AnchorPane mainPane;

    Stage mainStage;

    SubSceneManager subSceneManager;


    private static final int MENU_BUTTONS_START_X = 60;
    private static final int MENU_BUTTONS_START_Y = 150;

    public ButtonManager(Stage mainStage, AnchorPane mainPane, SubSceneManager subSceneManager){
        this.mainPane = mainPane;
        this.mainStage = mainStage;
        this.subSceneManager = subSceneManager;
        menuButtons = new ArrayList<>();
    }

    public void createButtons() {
        createPlayButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createSoundButton();
        createSwitchButton();
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

    private void createSwitchButton() {
        SnakeSwitchButton switchButton = new SnakeSwitchButton("Night", mainPane);
        switchButton.setLayoutX(750);
        switchButton.setLayoutY(40);
        mainPane.getChildren().add(switchButton);
        switchButton.setOnAction(event -> switchButton.toggle());
    }

    private void createPlayButton() {
        SnakeButton startButton = new SnakeButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(event -> subSceneManager.showThemeChooserSubScene());
    }

    private void createScoresButton() {
        SnakeButton scoresButton = new SnakeButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(event -> {
            subSceneManager.drawScoreTableOnSubScene();
            subSceneManager.showScoresSubScene();
        });
    }

    private void createHelpButton() {
        SnakeButton helpButton = new SnakeButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(event -> subSceneManager.showHelpSubScene());
    }

    private void createCreditsButton() {
        SnakeButton settingButton = new SnakeButton("Settings");
        addMenuButton(settingButton);

        settingButton.setOnAction(event -> {
            subSceneManager.drawSettingSubScene();
            subSceneManager.showSettingSubScene();
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
