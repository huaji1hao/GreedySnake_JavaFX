package com.megasnake.ui.controller;

import com.megasnake.game.controller.SnakeGameController;
import com.megasnake.game.model.User;
import com.megasnake.ui.component.CustomLabel;
import com.megasnake.ui.model.THEME;
import com.megasnake.ui.model.ThemePicker;
import com.megasnake.ui.component.InfoLabel;
import com.megasnake.ui.component.SnakeButton;
import com.megasnake.ui.component.SnakeSubScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class SubSceneController {
    private SnakeSubScene sceneToHide;
    private SnakeSubScene credistSubScene;
    private SnakeSubScene helpSubScene;
    private SnakeSubScene scoresSubScene;
    private SnakeSubScene themeChooserSubScene;

    PriorityQueue<User> userTable;

    List<ThemePicker> themesList;
    private THEME chosenTheme;

    private AnchorPane mainPane;

    private Stage mainStage;

    public SubSceneController(Stage mainStage, AnchorPane mainPane) {
        this.mainStage = mainStage;
        this.mainPane = mainPane;
        userTable = new PriorityQueue<>();
    }

    public void createSubScenes() {
        credistSubScene = new SnakeSubScene();
        mainPane.getChildren().add(credistSubScene);

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
                    gameController.runSnakeGame(mainStage, userTable, chosenTheme.getDifficulty());
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

    public void drawScoreTableOnSubScene() {
        VBox scoreLayout = new VBox(10); // Vertical layout with spacing of 10
        scoreLayout.setAlignment(Pos.CENTER); // Center alignment

        HBox titleBar = new HBox(50); // Horizontal layout with spacing of 50
        titleBar.setAlignment(Pos.CENTER); // Center alignment

        CustomLabel userTitle = new CustomLabel("User", 23);
        CustomLabel scoreTitle = new CustomLabel("Score", 23);

        titleBar.getChildren().addAll(userTitle, scoreTitle);
        scoreLayout.getChildren().add(titleBar);

        ArrayList<User> usersList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (userTable.isEmpty()) {
                break;
            }
            usersList.add(userTable.poll());
        }

        for (User user : usersList) {
            InfoLabel scoreLabel = new InfoLabel(user.getUsername() + "       " + user.getScore());
            scoreLayout.getChildren().add(scoreLabel);
            userTable.add(user); // Add back to the queue
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



    public void showCreditsSubScene() {
        showSubScene(credistSubScene);
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
