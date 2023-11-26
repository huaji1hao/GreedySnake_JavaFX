package com.megasnake.view.component;

import com.megasnake.model.THEME;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ThemePicker extends VBox {
    private final ImageView circleImage;

    private static final String CIRCLE_NOT_CHOSEN = "grey_circle.png";
    private static final String CIRCLE_CHOSEN = "circle_chosen.png";

    private final THEME theme;

    private boolean isCircleChosen;

    public ThemePicker(THEME theme) {
        circleImage = new ImageView(CIRCLE_NOT_CHOSEN);
        ImageView themeImage = new ImageView(theme.getUrl());
        Label themeLabel = new CustomLabel(theme.getLevel(), 23);


        this.theme = theme;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(18);
        this.getChildren().add(circleImage);
        this.getChildren().add(themeImage);
        this.getChildren().add(themeLabel);
    }

    public THEME getTheme() {
        return theme;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? CIRCLE_CHOSEN : CIRCLE_NOT_CHOSEN;
        circleImage.setImage(new Image(imageToSet));
    }
}
