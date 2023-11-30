package com.megasnake.view.component;

import com.megasnake.utils.ImageLoader;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * A custom sub scene for the MegaSnake game.
 */
public class SnakeSubScene extends SubScene{
    private boolean isHidden = true;

    /**
     * Creates a new SnakeSubScene object.
     */
    public SnakeSubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        String backgroundImage = "/component/green-panel.png";
        BackgroundImage image = new BackgroundImage(ImageLoader.getImage(backgroundImage),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        // highlight the sub scene a little when the mouse is over it
        setOnMouseEntered(event -> setEffect(new Glow(0.07)));
        setOnMouseExited(event -> setEffect(null));
        setLayoutX(1024);
        setLayoutY(180);
    }

    /**
     * Moves the sub scene to the left or right of the screen depending on its hidden state.
     */
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    /**
     * Returns the pane of the sub scene.
     *
     * @return The pane of the sub scene.
     */
    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

    /**
     * Moves the sub scene to the left of the screen.
     */
    public void moveSubSceneInGame() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        transition.setToX(-956);

        transition.play();
    }


}
