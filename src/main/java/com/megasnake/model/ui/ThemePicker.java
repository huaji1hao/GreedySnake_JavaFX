package com.megasnake.model.ui;

import com.megasnake.model.component.CustomLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ThemePicker extends VBox {
    private ImageView circleImage;
    private ImageView themeImage;
    private Label themeLabel;

    private String circleNotChoosen = "grey_circle.png";
    private String circleChoosen = "circle_choosen.png";

    private THEME THEME;

    private boolean isCircleChoosen;

    public ThemePicker(THEME THEME) {
        circleImage = new ImageView(circleNotChoosen);
        themeImage = new ImageView(THEME.getUrl());
        themeLabel = new CustomLabel(THEME.getLevel(), 23);


        this.THEME = THEME;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(18);
        this.getChildren().add(circleImage);
        this.getChildren().add(themeImage);
        this.getChildren().add(themeLabel);
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
