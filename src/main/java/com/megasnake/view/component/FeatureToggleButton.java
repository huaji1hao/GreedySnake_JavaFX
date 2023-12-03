package com.megasnake.view.component;

/**
 * A button that can be toggled on and off to enable or disable a feature.
 *
 * @author Junfeng ZHU
 */
public class FeatureToggleButton extends SnakeButton{
    private boolean isToggled;
    private static final String BUTTON_OPEN_STYLE = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";
    private static final String BUTTON_CLOSE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('red-button.png');";

    /**
     * Creates a new FeatureToggleButton.
     * @param text The text to display on the button.
     */
    public FeatureToggleButton(String text){
        super(text);
        isToggled = true;
        setStyle(BUTTON_OPEN_STYLE);
        muteButtonListener();
    }

    /**
     * Returns whether the button is toggled on or off.
     * @return True if the button is toggled on, false otherwise.
     */
    public boolean isSelected(){
        return isToggled;
    }

    /**
     * Toggles the button on or off and changes the button's style and text accordingly.
     */
    public void toggle(){
        isToggled = !isToggled;
        if(isToggled){
            setStyle(BUTTON_OPEN_STYLE);
            setText("Open");
        }else{
            setText("Close");
            setStyle(BUTTON_CLOSE_STYLE);
        }
    }

    /**
     * the button is not movable when pressed.
     */
    private void muteButtonListener(){
        setOnMousePressed(event -> {});
        setOnMouseReleased(event -> {});
    }


}
