package com.megasnake.view.ui;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.view.component.SnakeButton;
import com.megasnake.view.component.BackgroundToggleButton;
import com.megasnake.view.component.SoundButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the creation and functionality of buttons in the MegaSnake game's main menu.
 */
public class ButtonManager {
    private final List<SnakeButton> menuButtons;
    private final AnchorPane mainPane; // Main pane where buttons are added
    private final Stage mainStage; // Main stage of the application
    private final SubSceneManager subSceneManager; // Manager for sub-scenes

    private static final int MENU_BUTTONS_START_X = 60;
    private static final int MENU_BUTTONS_START_Y = 150;

    /**
     * Constructs a ButtonManager with the specified stage, pane, and sub-scene manager.
     *
     * @param mainStage The main stage of the application.
     * @param mainPane The main pane where buttons are to be added.
     * @param subSceneManager The manager for handling sub-scenes.
     */
    public ButtonManager(Stage mainStage, AnchorPane mainPane, SubSceneManager subSceneManager) {
        this.mainPane = mainPane;
        this.mainStage = mainStage;
        this.subSceneManager = subSceneManager;
        menuButtons = new ArrayList<>();
    }

    /**
     * Creates and adds all necessary buttons to the main menu.
     */
    public void createButtons() {
        createPlayButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createSoundButton();
        createSwitchButton();
        createExitButton();
    }

    /**
     * Adds a menu button to the main pane and positions it.
     *
     * @param button The button to be added to the menu.
     */
    private void addMenuButton(SnakeButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100.0);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    // Creates the sound toggle button
    private void createSoundButton() {
        SoundButton soundButton = new SoundButton();
        soundButton.setLayoutX(900);
        soundButton.setLayoutY(20);
        mainPane.getChildren().add(soundButton);
        soundButton.setOnAction(event -> soundButton.changeButtonStyleAndOperateMusic());
    }

    // Creates the background theme switch button
    private void createSwitchButton() {
        BackgroundToggleButton switchButton = new BackgroundToggleButton("Night", mainPane);
        switchButton.setLayoutX(750);
        switchButton.setLayoutY(40);
        mainPane.getChildren().add(switchButton);
        switchButton.setOnAction(event -> switchButton.toggle());
    }

    // Creates the play button and sets its action
    private void createPlayButton() {
        SnakeButton startButton = new SnakeButton("PLAY");
        addMenuButton(startButton);
        startButton.setOnAction(event -> subSceneManager.showThemeChooserSubScene());
    }

    // Creates the scores button and sets its action
    private void createScoresButton() {
        SnakeButton scoresButton = new SnakeButton("SCORES");
        addMenuButton(scoresButton);
        scoresButton.setOnAction(event -> {
            subSceneManager.drawScoreTableOnSubScene();
            subSceneManager.showScoresSubScene();
        });
    }

    // Creates the help button and sets its action
    private void createHelpButton() {
        SnakeButton helpButton = new SnakeButton("HELP");
        addMenuButton(helpButton);
        helpButton.setOnAction(event -> subSceneManager.showHelpSubScene());
    }

    // Creates the settings button and sets its action
    private void createCreditsButton() {
        SnakeButton settingButton = new SnakeButton("Settings");
        addMenuButton(settingButton);
        settingButton.setOnAction(event -> subSceneManager.showSettingSubScene());
    }

    // Creates the exit button and sets its action
    private void createExitButton() {
        SnakeButton exitButton = new SnakeButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnAction(event -> {
            BackgroundMusicPlayer.stopMusic();
            mainStage.close();
        });
    }
}
