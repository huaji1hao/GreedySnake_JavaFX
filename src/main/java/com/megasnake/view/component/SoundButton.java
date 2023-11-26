package com.megasnake.view.component;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;

public class SoundButton extends Button {
    private static final int BUTTON_HEIGHT = 82;
    private static final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button.png');";
    private static final String BUTTON_CLOSE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button_close.png');";

    public SoundButton() {
        initializeButton();
    }

    private void initializeButton() {
        int buttonWidth = 82;
        setPrefWidth(buttonWidth);
        setPrefHeight(BUTTON_HEIGHT);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }

    private  void setButtonPressed() {
        setPrefHeight(BUTTON_HEIGHT - 1.0);
        setLayoutY(getLayoutY() + 1);
    }

    private void setButtonReleased() {
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY() - 1);
    }

    public void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressed();
            }
        });

        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleased();
            }
        });

        setOnMouseEntered(event -> setEffect(new Glow()));

        setOnMouseExited(event -> setEffect(null));

    }

    public void changeButtonStyleAndOperateMusic() {
        if(!BackgroundMusicPlayer.isMuted()) {
            setStyle(BUTTON_CLOSE_STYLE);
            BackgroundMusicPlayer.operateMute();
        } else {
            setStyle(BUTTON_STYLE);
            BackgroundMusicPlayer.operateMute();
        }
    }

}
