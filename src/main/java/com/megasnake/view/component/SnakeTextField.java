package com.megasnake.view.component;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.util.Objects;

public class SnakeTextField extends TextField {

    public SnakeTextField() {
        // Set default prompt text
        this.setPromptText("Enter Username");

        // Set maximum length to 10
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                // Revert text to the old value
                this.setText(oldValue);
            }
        });

        // the size of the TextField
        this.setPrefWidth(300); // Width
        this.setPrefHeight(50); // Height

        // Set font size and style
        setFont(23);
    }

    public void setPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    // Method to get the text field's value
    public String getTextValue() {
        return this.getText();
    }

    private void setFont(double fontSize) {
        // Attempt to load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), fontSize);
        // Use Arial as a fallback if custom font fails to load
        this.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }
}

