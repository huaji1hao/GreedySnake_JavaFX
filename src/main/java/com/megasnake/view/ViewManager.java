package com.megasnake.view;

import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.BackgroundMusicPlayer;
import com.megasnake.utils.audio.SoundEffectPlayer;
import com.megasnake.view.ui.ButtonManager;
import com.megasnake.view.ui.SubSceneManager;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Manages the main view of the MegaSnake game.
 */
public class ViewManager {
    private static final int WIDTH = 1024; // Width of the main stage
    private static final int HEIGHT = 700; // Height of the main stage
    private final AnchorPane mainPane; // Main container for UI elements
    private final Stage mainStage; // Primary stage for the application

    /**
     * Constructs a ViewManager, setting up the game's main stage and UI elements.
     */
    public ViewManager() {
        // Preload images and media for faster access and smoother playback
        ImageLoader.preloadImages();
        SoundEffectPlayer.preloadMedia();

        mainPane = new AnchorPane();
        Scene mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.setTitle("MegaSnake");
        mainStage.getIcons().add(ImageLoader.getImage("/component/little-logo.png"));

        // Initialize and set up sub-scenes and buttons
        SubSceneManager subSceneManager = new SubSceneManager(mainStage, mainPane);
        subSceneManager.createSubScenes();
        ButtonManager buttonManager = new ButtonManager(mainStage, mainPane, subSceneManager);
        buttonManager.createButtons();

        // Set up the main stage
        createBackground();
        createLogo();
        BackgroundMusicPlayer.repeatMusic("/audio/ui-background.mp3");
    }

    /**
     * Returns the main stage of the game.
     *
     * @return The main stage.
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Creates and sets the background for the main stage.
     */
    private void createBackground() {
        try {
            // Load and set the background image
            Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/background/jungle2.png")).toURI().toString(), 1024, 700, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }
    }

    /**
     * Creates and adds the game logo to the main pane.
     */
    private void createLogo() {
        // Set up the logo with mouse hover effects
        ImageView logo = new ImageView(ImageLoader.getImage("/component/megasnake.png"));
        logo.setLayoutX(400);
        logo.setLayoutY(95);
        logo.setOnMouseEntered(event -> logo.setEffect(new Glow()));
        logo.setOnMouseExited(event -> logo.setEffect(null));

        mainPane.getChildren().add(logo);
    }
}
