package com.megasnake.view.component;

public class SnakeToggleButton extends SnakeButton{

    private boolean isToggled;
    private static final String BUTTON_OPEN_STYLE = "-fx-background-color: transparent; -fx-background-image: url('qing-button.png');";
    private static final String BUTTON_CLOSE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('red-button.png');";

    public SnakeToggleButton(String text){
        super(text);
        isToggled = true;
        setStyle(BUTTON_OPEN_STYLE);
        muteButtonListener();
    }

    public boolean isSelected(){
        return isToggled;
    }

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

    private void muteButtonListener(){
        setOnMousePressed(event -> {});
        setOnMouseReleased(event -> {});
    }


}
