package com.megasnake.view.ui;

import com.megasnake.controller.SnakeGameController;
import com.megasnake.model.User;
import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.scoreboard.ScoreReader;
import com.megasnake.model.THEME;
import com.megasnake.view.component.*;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the sub-scenes within the MegaSnake game's main menu.
 *
 * @author Junfeng ZHU
 */
public class SubSceneManager {
    private SnakeSubScene sceneToHide;
    private SnakeSubScene settingSubScene;
    private SnakeSubScene helpSubScene;
    private SnakeSubScene scoresSubScene;
    private SnakeSubScene themeChooserSubScene;

    List<ThemePicker> themesList;
    private THEME chosenTheme;

    private final AnchorPane mainPane;

    private final Stage mainStage;


    /**
     * Constructs a SubSceneManager with a given stage and pane.
     *
     * @param mainStage The main stage of the application.
     * @param mainPane  The main pane where sub-scenes will be displayed.
     */
    public SubSceneManager(Stage mainStage, AnchorPane mainPane) {
        this.mainStage = mainStage;
        this.mainPane = mainPane;
    }

    /**
     * Creates and adds all sub-scenes to the main pane.
     */
    public void createSubScenes() {
        settingSubScene = new SnakeSubScene();
        createSettingSubScene();
        mainPane.getChildren().add(settingSubScene);

        helpSubScene = new SnakeSubScene();
        createHelpSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoresSubScene = new SnakeSubScene();
        mainPane.getChildren().add(scoresSubScene);

        themeChooserSubScene = new SnakeSubScene();
        createThemeChooserSubScene();
        mainPane.getChildren().add(themeChooserSubScene);
    }

    // Creates the theme chooser sub-scene with theme options.
    private void createThemeChooserSubScene() {
        InfoLabelBoard chooseThemeLabel = new InfoLabelBoard("CHOOSE YOUR THEME");
        chooseThemeLabel.setLayoutX(110);
        chooseThemeLabel.setLayoutY(25);
        themeChooserSubScene.getPane().getChildren().add(chooseThemeLabel);
        themeChooserSubScene.getPane().getChildren().add(createThemesToChoose());
        themeChooserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    // Creates the start button for the theme chooser sub-scene.
    private SnakeButton createButtonToStart(){
        SnakeButton startButton = new SnakeButton("START");
        startButton.setButtonStyleQing();

        startButton.setLayoutX(350);
        startButton.setLayoutY(280);

        // Start the game with the chosen theme if the chosen theme is not null
        startButton.setOnAction(event -> {
            if (chosenTheme != null) {
                SnakeGameController gameController = new SnakeGameController(mainStage);
                gameController.runSnakeGame(chosenTheme.getLevel());
            }
        });


        return startButton;
    }

    // Creates a horizontal box with theme options.
    private HBox createThemesToChoose() {
        HBox box = new HBox();
        box.setSpacing(30);
        themesList = new ArrayList<>();

        for(THEME THEME : THEME.values()) {
            ThemePicker themeToPick = new ThemePicker(THEME);
            themesList.add(themeToPick);
            box.getChildren().add(themeToPick);

            themeToPick.setOnMouseClicked(event -> {
                for(ThemePicker theme : themesList) {
                    theme.setIsCircleChosen(false);
                }
                themeToPick.setIsCircleChosen(true);
                chosenTheme = themeToPick.getTheme();
            });
        }
        box.setLayoutX(300.0 - (118*2));
        box.setLayoutY(100);
        return box;
    }

    // Shows a specific sub-scene, hiding the previous one if necessary.
    private void showSubScene(SnakeSubScene subScene) {
        if(sceneToHide != null && sceneToHide.equals(subScene)) {
            subScene.moveSubScene();
            sceneToHide = null;
            return;
        }

        if(sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    // Initializes the help sub-scene with a background image.
    public void createHelpSubScene(){
        String backgroundImage = "/component/help.png";
        BackgroundImage image = new BackgroundImage(ImageLoader.getImage(backgroundImage),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        helpSubScene.getPane().setBackground(new Background(image));
    }

    // Initializes the settings sub-scene with UI components.
    private void createSettingSubScene(){
        FeatureToggleButton specialItemToggle = new FeatureToggleButton("Open"); // Default text
        FeatureToggleButton aiSnakeToggle = new FeatureToggleButton("Open"); // Default text
        Slider foodNumSlider = new Slider(1, 5, 2); // Minimum 1, Maximum 5, Initial value 2
        CustomLabel button1Text = new CustomLabel("Special Items", 23);
        CustomLabel button2Text = new CustomLabel("AI Snake Competitor", 23);
        CustomLabel sliderText = new CustomLabel("Food Number", 23);

        foodNumSlider.setShowTickLabels(true); // Tick labels are shown
        foodNumSlider.setShowTickMarks(true); // Tick marks are shown
        foodNumSlider.setMajorTickUnit(1); // Major tick unit set to 1
        foodNumSlider.setMinorTickCount(0); // No minor ticks
        foodNumSlider.setSnapToTicks(true); // Slider value will snap to the nearest tick mark

        VBox settingsLayout = new VBox(10);
        settingsLayout.setAlignment(Pos.CENTER);

        // Toggle button listeners
        specialItemToggle.setOnAction(event -> {
            specialItemToggle.toggle();
            SnakeGameController.setPlayableFeature(specialItemToggle.isSelected());
        });

        aiSnakeToggle.setOnAction(event -> {
            aiSnakeToggle.toggle();
            SnakeGameController.setAISnake(aiSnakeToggle.isSelected());
        });

        // Slider listener
        foodNumSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int foodNum = newValue.intValue();
            SnakeGameController.setFoodNum(foodNum);
        });

        settingsLayout.getChildren().addAll(button1Text, specialItemToggle, button2Text, aiSnakeToggle, sliderText, foodNumSlider);
        settingSubScene.getPane().getChildren().add(settingsLayout);

        settingsLayout.setLayoutX(150);
        settingsLayout.setLayoutY(70);
    }

    // Draws the score table in the scores sub-scene.
    public void drawScoreTableOnSubScene() {
        VBox scoreLayout = new VBox(10);
        scoreLayout.setAlignment(Pos.CENTER); // Center alignment

        HBox titleBar = new HBox(50);
        titleBar.setAlignment(Pos.CENTER); // Center alignment

        CustomLabel userTitle = new CustomLabel("User", 23);
        CustomLabel scoreTitle = new CustomLabel("Score", 23);

        titleBar.getChildren().addAll(userTitle, scoreTitle);
        scoreLayout.getChildren().add(titleBar);

        // Read scores from file and sort them
        List<User> usersList = ScoreReader.readScoresFromFile();
        Collections.sort(usersList);

        // Only show the top 5 scores
        if (usersList.size() > 5) usersList = new ArrayList<>(usersList.subList(0, 5));


        // Add the scores to the sub scene
        for (User user : usersList) {
            InfoLabelBoard scoreLabel = new InfoLabelBoard(user.getUsername() + "       " + user.getScore());
            scoreLayout.getChildren().add(scoreLabel);
        }

        // If there are fewer than five scores, add blank labels
        while (usersList.size() < 5) {
            scoreLayout.getChildren().add(new InfoLabelBoard(""));
            usersList.add(new User("", 0)); // Assume score of 0
        }

        scoresSubScene.getPane().getChildren().add(scoreLayout);

        scoreLayout.setLayoutX((600 - 380) / 2.0);
        scoreLayout.setLayoutY(15);

    }

    public void showSettingSubScene() {
        showSubScene(settingSubScene);
    }

    public void showHelpSubScene() {
        showSubScene(helpSubScene);
    }

    public void showScoresSubScene() {
        showSubScene(scoresSubScene);
    }

    public void showThemeChooserSubScene() {
        showSubScene(themeChooserSubScene);
    }


}
