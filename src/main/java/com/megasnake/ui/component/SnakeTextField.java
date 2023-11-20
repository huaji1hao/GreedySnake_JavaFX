package com.megasnake.ui.component;

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

        // Set the position and size of the TextField
        this.setLayoutX(150); // X coordinate
        this.setLayoutY(180); // Y coordinate
        this.setPrefWidth(300); // Width
        this.setPrefHeight(50); // Height

        // Set font size and style
        setFont(23);
    }

    // Method to get the text field's value
    public String getTextValue() {
        return this.getText();
    }

    private void setFont(double fontSize) {
        String FONT_PATH = "/font/kenvector_future.ttf"; // Path to the custom font file

        // Attempt to load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), fontSize);
        // Use Arial as a fallback if custom font fails to load
        this.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }
}

