package com.megasnake.view.component;

import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import java.util.Objects;

/**
 * A button that toggles between day and night background.
 */
public class BackgroundToggleButton extends SnakeButton{
    private boolean isToggled;
    private final AnchorPane mainPane;
    public static final int DAY = 0;
    public static final int NIGHT = 1;
    private static final String BUTTON_DAY_STYLE = "-fx-background-color: transparent; -fx-background-image: url('blue-button.png');";
    private static final String BUTTON_NIGHT_STYLE = "-fx-background-color: transparent; -fx-background-image: url('purple-button.png');";

    /**
     * Creates a new BackgroundToggleButton.
     *
     * @param text The text to display on the button.
     * @param mainPane The main pane of the game.
     */
    public BackgroundToggleButton(String text, AnchorPane mainPane) {
        super(text);
        setPrefWidth(117);
        setPrefHeight(47);
        isToggled = false;
        this.mainPane = mainPane;
        setStyle(BUTTON_NIGHT_STYLE);

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleased();
            }
        });
    }

    /**
     * Toggles the button.
     */
    public void toggle(){
        isToggled = !isToggled;
        if(isToggled){
            setStyle(BUTTON_DAY_STYLE);
            setText("Day");
            setBackground(DAY);
        }else{
            setText("Night");
            setStyle(BUTTON_NIGHT_STYLE);
            setBackground(NIGHT);
        }
    }

    /**
     * Sets the background of the game.
     *
     * @param style The style of the background.
     */
    private void setBackground(int style){
        try{
            Image backgroundImage;
            if(style == DAY)
                backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/background/jungle1.png")).toURI().toString(), 1024, 700, false, true);
            else
                backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/background/jungle2.png")).toURI().toString(), 1024, 700, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        }catch (Exception e){
            System.out.println("Error loading background image: " + e.getMessage());
        }

    }

    /**
     * Because the size of the button is different from its father, it needs to be adjusted.
     */
    private void setButtonReleased() {
        setPrefHeight(47);
        setLayoutY(getLayoutY() - 4);
    }
}
