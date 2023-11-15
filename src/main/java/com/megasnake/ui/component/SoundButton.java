package com.megasnake.ui.component;

import com.megasnake.audio.BackgroundMusicPlayer;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SoundButton extends Button {
    private int BUTTON_HEIGHT = 82;
    private int BUTTON_WIDTH = 82;
    private String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button.png');";
    private String BUTTON_CLOSE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button_close.png');";

    public SoundButton() {
        initializeButton();
    }

    private void initializeButton() {
        setPrefWidth(BUTTON_WIDTH);
        setPrefHeight(BUTTON_HEIGHT);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }

    private  void setButtonPressed() {
        setPrefHeight(BUTTON_HEIGHT - 1);
        setLayoutY(getLayoutY() + 1);
    }

    private void setButtonReleased() {
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY() - 1);
    }

    public void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressed();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleased();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new Glow());
            }

        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });

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
