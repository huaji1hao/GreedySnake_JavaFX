package com.megasnake.view.component;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * A custom label class that allows for easy customization of the font.
 */
public class CustomLabel extends Label {

    /**
     * Constructs a CustomLabel with specified text and font size.
     *
     * @param text      The text to be displayed.
     * @param fontSize  The size of the font.
     */
    public CustomLabel(String text, double fontSize) {
        super(text); // Call the superclass constructor to set the text
        setFont(fontSize); // Set the font size
    }

    /**
     * Sets the font of the label with the specified size.
     * Tries to load a custom font, and if it fails, defaults to Arial.
     *
     * @param fontSize The size of the font to be set.
     */
    private void setFont(double fontSize) {
        // Attempt to load the custom font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/font/kenvector_future.ttf"), fontSize);
        // Use Arial as a fallback if custom font fails to load
        this.setFont(Objects.requireNonNullElseGet(customFont, () -> new Font("Arial", fontSize)));
    }

    /**
     * Sets the position of the label.
     *
     * @param x The x-coordinate of the label.
     * @param y The y-coordinate of the label.
     */
    public void setPos(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

}
