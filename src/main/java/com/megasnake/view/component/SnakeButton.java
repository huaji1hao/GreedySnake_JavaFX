package com.megasnake.view.component;

import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

/**
 * Custom button class for the MegaSnake game.
 *
 * @Author Junfeng ZHU
 */
public class SnakeButton extends Button {
    private static final String GREEN_BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('green-button.png');";
    private static final String QING_BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";

    /**
     * Initializes a new instance of the SnakeButton class.
     *
     * @param text The text to display on the button.
     */
    public SnakeButton(String text) {
        initializeButton(text);
    }

    /**
     * Sets the button style to the default green button style.
     */
    private void initializeButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(GREEN_BUTTON_STYLE);
        initializeButtonListeners();
    }

    /**
     * Sets the button style to another green button style.
     */
    public void setButtonStyleQing() {
        setStyle(QING_BUTTON_STYLE);
    }

    /**
     * Sets the button font to the KenVector Future font.
     */
    private void setButtonFont() {
        try {
            setFont(Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), 23));
        } catch (Exception e) {
            System.out.println("Font not found");
            setFont(Font.font("Verdana", 23));
        }

    }

    /**
     * Sets the button style to the pressed style.
     */
    private  void setButtonPressedStyle() {
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    /**
     * Sets the button style to the released style.
     */
    private void setButtonReleasedStyle() {
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    /**
     * Initializes the button listeners.
     * Sets the button style to the pressed style when the button is pressed.
     * Sets the button style to the released style when the button is released.
     * <p>
     * Sets the button style to the glow style when the mouse enters the button.
     * Sets the button style to the default style when the mouse exits the button.
     *
     * @see #setButtonPressedStyle()
     * @see #setButtonReleasedStyle()
     */
    public void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(event -> setEffect(new Glow()));
        setOnMouseExited(event -> setEffect(null));

    }


}
