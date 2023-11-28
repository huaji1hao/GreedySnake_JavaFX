package com.megasnake.view.component;

import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.Objects;

public class SnakeSwitchButton extends SnakeButton{
    private boolean isToggled;
    private final AnchorPane mainPane;
    private static final String BUTTON_DAY_STYLE = "-fx-background-color: transparent; -fx-background-image: url('blue-button.png');";
    private static final String BUTTON_NIGHT_STYLE = "-fx-background-color: transparent; -fx-background-image: url('purple-button.png');";

    public SnakeSwitchButton(String text, AnchorPane mainPane) {
        super(text);
        setPrefWidth(117);
        setPrefHeight(47);
        isToggled = false;
        this.mainPane = mainPane;
        setStyle(BUTTON_NIGHT_STYLE);

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleased();
            }
        });
    }


    public void toggle(){
        isToggled = !isToggled;
        if(isToggled){
            setStyle(BUTTON_DAY_STYLE);
            setText("Day");
            createBackground(0);
        }else{
            setText("Night");
            setStyle(BUTTON_NIGHT_STYLE);
            createBackground(1);
        }
    }

    private void createBackground(int style){
        try{
            Image backgroundImage;
            if(style == 0)
                backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/background/jungle1.png")).toURI().toString(), 1024, 700, false, true);
            else
                backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/background/jungle2.png")).toURI().toString(), 1024, 700, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        }catch (Exception e){
            System.out.println("Error loading background image: " + e.getMessage());
        }

    }

    private void setButtonReleased() {
        setPrefHeight(47);
        setLayoutY(getLayoutY() - 4);
    }
}
