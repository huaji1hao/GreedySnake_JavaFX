package com.megasnake.ui.controller;

import com.megasnake.game.controller.SnakeGameController;
import com.megasnake.ui.model.THEME;
import com.megasnake.ui.model.ThemePicker;
import com.megasnake.ui.view.InfoLabel;
import com.megasnake.ui.view.SnakeButton;
import com.megasnake.ui.view.SnakeSubScene;
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

    PriorityQueue<Integer> scoreTable;

    List<ThemePicker> themesList;
    private THEME chosenTheme;

    private AnchorPane mainPane;

    private Stage mainStage;

    public SubSceneController(Stage mainStage, AnchorPane mainPane) {
        this.mainStage = mainStage;
        this.mainPane = mainPane;
        scoreTable = new PriorityQueue<>(Collections.reverseOrder());
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
        SnakeButton startButton = new SnakeButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(chosenTheme != null) {
                    SnakeGameController gameController = new SnakeGameController();
                    gameController.runSnakeGame(mainStage, scoreTable, chosenTheme.getDifficulty());
                }
            }
        });


        return startButton;
    }

    private HBox createThemesToChoose() {
        HBox box = new HBox();
        box.setSpacing(20);
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
        VBox scoreLayout = new VBox(10); // 垂直布局，元素间距为10
        scoreLayout.setAlignment(Pos.CENTER); // 居中对齐

        ArrayList<Integer> scoresList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (scoreTable.isEmpty()) {
                break;
            }
            scoresList.add(scoreTable.poll());
        }

        for (Integer score : scoresList) {
            InfoLabel scoreLabel = new InfoLabel(score.toString());
            scoreLayout.getChildren().add(scoreLabel);
            scoreTable.add(score); // 添加回去
        }

        // 如果分数不足五个，添加空白标签
        while (scoresList.size() < 5) {
            scoreLayout.getChildren().add(new InfoLabel(""));
            scoresList.add(0); // 假设分数为0
        }

        scoresSubScene.getPane().getChildren().add(scoreLayout);

        // 计算VBox的起始位置以使其在subscene中垂直居中
        double totalLabelHeight = scoresList.size() * 49 + (scoresList.size() - 1) * 10; // 总高度 = 标签高度 * 数量 + 间距 * (数量 - 1)
        scoreLayout.setLayoutX((600 - 380) / 2); // 水平居中 (subscene宽度 - InfoLabel宽度) / 2
        scoreLayout.setLayoutY((400 - totalLabelHeight) / 2); // 垂直居中 (subscene高度 - totalLabelHeight) / 2
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
