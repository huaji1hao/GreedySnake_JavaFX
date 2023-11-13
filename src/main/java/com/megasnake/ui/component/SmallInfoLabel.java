package com.megasnake.ui.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label {
    private final static String FONT_PATH = "/font/kenvector_future.ttf";

    public SmallInfoLabel(String text) {
        setPrefWidth(130);
        setPrefHeight(50);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("blue_info_label.png", 130, 50, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setText(text);
//        setWrapText(true);
        setLabelFont();
//        setStyle("-fx-background-color: transparent");
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 15));
        } catch (Exception e) {
            setFont(Font.font("Verdana", 10));
        }

   }

}
