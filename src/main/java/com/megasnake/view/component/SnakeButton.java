package com.megasnake.view.component;

import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

public class SnakeButton extends Button {
    private String buttonFreeStyle = "-fx-background-color: transparent; -fx-background-image: url('green-button.png');";

    public SnakeButton(String text) {
        initializeButton(text);
    }

    public SnakeButton(String text, int color) {
        if(color == 1) {
            buttonFreeStyle = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";
        }
        initializeButton(text);

    }

    protected void initializeButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(buttonFreeStyle);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), 23));
        } catch (Exception e) {
            System.out.println("Font not found");
            setFont(Font.font("Verdana", 23));
        }

    }

    private  void setButtonPressedStyle() {
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

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
