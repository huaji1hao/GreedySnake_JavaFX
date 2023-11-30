package com.megasnake.view.component;

import com.megasnake.model.THEME;
import com.megasnake.utils.ImageLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A component that represents a theme picker.
 */
public class ThemePicker extends VBox {
    private final ImageView circleImage;
    private static final String CIRCLE_NOT_CHOSEN = "/component/grey_circle.png";
    private static final String CIRCLE_CHOSEN = "/component/circle_chosen.png";
    private final THEME theme;
    private boolean isCircleChosen;

    /**
     * Creates a new theme picker.
     *
     * @param theme The theme to be displayed.
     */
    public ThemePicker(THEME theme) {
        circleImage = new ImageView(ImageLoader.getImage(CIRCLE_NOT_CHOSEN));
        ImageView themeImage = new ImageView(ImageLoader.getImage(theme.getUrl()));
        Label themeLabel = new CustomLabel(theme.getLevelText(), 23);


        this.theme = theme;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(18);
        this.getChildren().add(circleImage);
        this.getChildren().add(themeImage);
        this.getChildren().add(themeLabel);
    }

    /**
     * Returns the theme of this theme picker.
     *
     * @return The theme of this theme picker.
     */
    public THEME getTheme() {
        return theme;
    }

    /**
     * Sets the chosen state of this theme picker.
     * If the theme picker is chosen, the circle image will be changed to a chosen circle.
     * Otherwise, the circle image will be changed to a not chosen circle.
     *
     * @param isCircleChosen The chosen state of this theme picker.
     */
    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? CIRCLE_CHOSEN : CIRCLE_NOT_CHOSEN;
        circleImage.setImage(ImageLoader.getImage(imageToSet));
    }
}
