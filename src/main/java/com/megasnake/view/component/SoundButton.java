package com.megasnake.view.component;

import com.megasnake.utils.audio.BackgroundMusicPlayer;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;

/**
 * A button that toggles the background music on and off.
 */
public class SoundButton extends Button {
    private static final int BUTTON_HEIGHT = 82;
    private static final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button.png');";
    private static final String BUTTON_CLOSE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('sound_button_close.png');";

    /**
     * Creates a new sound button.
     */
    public SoundButton() {
        initializeButton();
    }

    /**
     * Initializes the button.
     */
    private void initializeButton() {
        int buttonWidth = 82;
        setPrefWidth(buttonWidth);
        setPrefHeight(BUTTON_HEIGHT);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }

    /**
     * Sets the button to the pressed state.
     */
    private  void setButtonPressed() {
        setPrefHeight(BUTTON_HEIGHT - 1.0);
        setLayoutY(getLayoutY() + 1);
    }

    /**
     * Sets the button to the released state.
     */
    private void setButtonReleased() {
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY() - 1);
    }

    /**
     * Initializes the button listeners.
     */
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

        // highlight button when mouse enters
        setOnMouseEntered(event -> setEffect(new Glow()));

        // remove highlight when mouse exits
        setOnMouseExited(event -> setEffect(null));

    }

    /**
     * Changes the button style and operates the music.
     */
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
