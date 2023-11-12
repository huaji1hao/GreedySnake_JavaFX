package com.megasnake.ui.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class SnakeButton extends Button {
    private final String FONT_PATH = "/font/kenvector_future.ttf";
    private String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('green-button.png');";
    private String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('green-button.png');";

    public SnakeButton(String text) {
        initializeButton(text);
    }

    public SnakeButton(String text, int color) {
        if(color == 1) {
            BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";
            BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";
        }
        initializeButton(text);

    }

    private void initializeButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));
        } catch (Exception e) {
            System.out.println("Font not found");
            setFont(Font.font("Verdana", 23));
        }

    }

    private  void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    public void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                setEffect(new DropShadow());
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


}
