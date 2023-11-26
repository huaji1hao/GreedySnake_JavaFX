package com.megasnake.controller.ui;

import com.megasnake.controller.game.SnakeGameController;
import com.megasnake.model.game.User;
import com.megasnake.utils.scoreboard.ScoreReader;
import com.megasnake.model.component.CustomLabel;
import com.megasnake.model.ui.THEME;
import com.megasnake.model.ui.ThemePicker;
import com.megasnake.model.component.InfoLabel;
import com.megasnake.model.component.SnakeButton;
import com.megasnake.model.component.SnakeSubScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubSceneController {
    private SnakeSubScene sceneToHide;
    private SnakeSubScene settingSubScene;
    private SnakeSubScene helpSubScene;
    private SnakeSubScene scoresSubScene;
    private SnakeSubScene themeChooserSubScene;

    List<ThemePicker> themesList;
    private THEME chosenTheme;

    private AnchorPane mainPane;

    private Stage mainStage;
    private ToggleButton specialItemToggle;
    private ToggleButton aiSnakeToggle;
    private Slider foodNumSlider;
    private CustomLabel button1Text;
    private CustomLabel button2Text;
    private CustomLabel sliderText;


    public SubSceneController(Stage mainStage, AnchorPane mainPane) {
        this.mainStage = mainStage;
        this.mainPane = mainPane;
        initializeSettingSubScene();
    }

    private void initializeSettingSubScene(){
        specialItemToggle = new ToggleButton("Open"); // Default text
        aiSnakeToggle = new ToggleButton("Open");
        foodNumSlider = new Slider(1, 5, 2); // Minimum 1, Maximum 5, Initial value 2
        button1Text = new CustomLabel("Special Items", 23);
        button2Text = new CustomLabel("AI Snake Competitor", 23);
        sliderText = new CustomLabel("Food Number", 23);
        specialItemToggle.setSelected(true); // Default state is selected (Open)
        aiSnakeToggle.setSelected(true);
        foodNumSlider.setShowTickLabels(true);
        foodNumSlider.setShowTickMarks(true);
        foodNumSlider.setMajorTickUnit(1); // Major tick unit set to 1
        foodNumSlider.setMinorTickCount(0); // No minor ticks
        foodNumSlider.setSnapToTicks(true); // Slider value will snap to the nearest tick mark
    }

    public void createSubScenes() {
        settingSubScene = new SnakeSubScene();
        mainPane.getChildren().add(settingSubScene);

        helpSubScene = new SnakeSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoresSubScene = new SnakeSubScene();
        mainPane.getChildren().add(scoresSubScene);

        createThemeChooserSubScene();
    }

    private void createThemeChooserSubScene() {
        themeChooserSubScene = new SnakeSubScene();
        mainPane.getChildren().add(themeChooserSubScene);

        InfoLabel chooseThemeLabel = new InfoLabel("CHOOSE YOUR THEME");
        chooseThemeLabel.setLayoutX(110);
        chooseThemeLabel.setLayoutY(25);
        themeChooserSubScene.getPane().getChildren().add(chooseThemeLabel);
        themeChooserSubScene.getPane().getChildren().add(createThemesToChoose());
        themeChooserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private SnakeButton createButtonToStart(){
        SnakeButton startButton = new SnakeButton("START", 1);
        startButton.setLayoutX(350);
        startButton.setLayoutY(280);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(chosenTheme != null) {
                    SnakeGameController gameController = new SnakeGameController();
                    gameController.runSnakeGame(mainStage, chosenTheme.getDifficulty());
                }
            }
        });


        return startButton;
    }

    private HBox createThemesToChoose() {
        HBox box = new HBox();
        box.setSpacing(30);
        themesList = new ArrayList<>();

        for(THEME THEME : THEME.values()) {
            ThemePicker themeToPick = new ThemePicker(THEME);
            themesList.add(themeToPick);
            box.getChildren().add(themeToPick);

            themeToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for(ThemePicker theme : themesList) {
                        theme.setIsCircleChoosen(false);
                    }
                    themeToPick.setIsCircleChoosen(true);
                    chosenTheme = themeToPick.getTheme();
                }
            });
        }
        box.setLayoutX(300 - (118*2));
        box.setLayoutY(100);
        return box;
    }

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

    public void drawSettingSubScene() {
        VBox settingsLayout = new VBox(20); // 垂直布局，间距为20
        settingsLayout.setAlignment(Pos.CENTER); // 居中对齐

        specialItemToggle.setOnAction(event -> {
            if (specialItemToggle.isSelected()) {
                specialItemToggle.setText("Open");
                SnakeGameController.setPlayableFeature(true);

            } else {
                specialItemToggle.setText("Close");
                SnakeGameController.setPlayableFeature(false);

            }
        });

        aiSnakeToggle.setOnAction(event -> {

            if (aiSnakeToggle.isSelected()) {
                // If toggle is selected (On), set text to "Open"
                aiSnakeToggle.setText("Open");
                SnakeGameController.setAISnake(true);


            } else {
                // If toggle is not selected (Off), set text to "Close"
                aiSnakeToggle.setText("Close");
                SnakeGameController.setAISnake(false);


            }
        });

        foodNumSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int foodNum = newValue.intValue();
            SnakeGameController.setFoodNum(foodNum);
        });


        settingsLayout.getChildren().addAll(button1Text, specialItemToggle, button2Text, aiSnakeToggle, sliderText, foodNumSlider);

        // 将设置布局添加到子场景中
        settingSubScene.getPane().getChildren().add(settingsLayout);

        // 设置布局位置（根据需要调整）
        settingsLayout.setLayoutX(150); // 水平位置
        settingsLayout.setLayoutY(70); // 垂直位置
    }



    public void drawScoreTableOnSubScene() {
        VBox scoreLayout = new VBox(10); // Vertical layout with spacing of 10
        scoreLayout.setAlignment(Pos.CENTER); // Center alignment

        HBox titleBar = new HBox(50); // Horizontal layout with spacing of 50
        titleBar.setAlignment(Pos.CENTER); // Center alignment

        CustomLabel userTitle = new CustomLabel("User", 23);
        CustomLabel scoreTitle = new CustomLabel("Score", 23);

        titleBar.getChildren().addAll(userTitle, scoreTitle);
        scoreLayout.getChildren().add(titleBar);

        ArrayList<User> usersList = ScoreReader.readScoresFromFile();
        Collections.sort(usersList);

        if (usersList.size() > 5) usersList = new ArrayList<>(usersList.subList(0, 5));


        for (User user : usersList) {
            InfoLabel scoreLabel = new InfoLabel(user.getUsername() + "       " + user.getScore());
            scoreLayout.getChildren().add(scoreLabel);
        }

        // If there are fewer than five scores, add blank labels
        while (usersList.size() < 5) {
            scoreLayout.getChildren().add(new InfoLabel(""));
            usersList.add(new User("", 0)); // Assume score of 0
        }

        scoresSubScene.getPane().getChildren().add(scoreLayout);

        scoreLayout.setLayoutX((600 - 380) / 2); // Horizontal centering (subscene width - InfoLabel width) / 2
        scoreLayout.setLayoutY(15); // Vertical centering (subscene height - totalLabelHeight) / 2

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

    public THEME getChosenTheme() {
        return chosenTheme;
    }


}
