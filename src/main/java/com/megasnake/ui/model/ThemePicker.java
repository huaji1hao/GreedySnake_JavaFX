package com.megasnake.ui.model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ThemePicker extends VBox {
    private ImageView circleImage;
    private ImageView shipImage;

    private String circleNotChoosen = "grey_circle.png";
    private String circleChoosen = "circle_choosen.png";

    private THEME THEME;

    private boolean isCircleChoosen;

    public ThemePicker(THEME THEME) {
        circleImage = new ImageView(circleNotChoosen);
        shipImage = new ImageView(THEME.getUrl());
        this.THEME = THEME;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(shipImage);
    }

    public THEME getTheme() {
        return THEME;
    }

    public boolean getIsCircleChoosen() {
        return isCircleChoosen;
    }

    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
