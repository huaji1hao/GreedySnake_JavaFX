package com.megasnake.utils;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for loading images.
 */
public class ImageLoader {
    // Prevent instantiation
    private ImageLoader() {}
    private static final Map<String, Image> imageCache = new HashMap<>();

    private static final String[] BACKGROUND_IMAGES = {"/background/candy_background.png", "/background/lava_background.png", "/background/deep_blue_star.png",
            "/background/candy_background_small.png", "/background/grass_background_small.png", "/background/lava_background_small.jpg", "/background/space_background_small.jpg"};
    private static final String[] COMPONENT_IMAGES = {"/component/circle_chosen.png", "/component/green-panel.png",
            "/component/grey_circle.png", "/component/help.png", "/component/little-logo.png", "/component/megasnake.png", "/component/help.png",
    };
    private static final String[][] SNAKE_IMAGES = {
            {"/snake/plain-snake/snake-body.png", "/snake/plain-snake/snake-head-right.png", "/snake/plain-snake/snake-head-left.png", "/snake/plain-snake/snake-head-up.png", "/snake/plain-snake/snake-head-down.png"},
            {"/snake/rabbit-snake/snake-body.png", "/snake/rabbit-snake/snake-head-right.png", "/snake/rabbit-snake/snake-head-left.png", "/snake/rabbit-snake/snake-head-up.png", "/snake/rabbit-snake/snake-head-down.png"},
            {"/snake/ghost-snake/snake-body.png", "/snake/ghost-snake/snake-head-right.png", "/snake/ghost-snake/snake-head-left.png", "/snake/ghost-snake/snake-head-up.png", "/snake/ghost-snake/snake-head-down.png"},
            {"/snake/space-snake/snake-body.png", "/snake/space-snake/snake-head-right.png", "/snake/space-snake/snake-head-left.png", "/snake/space-snake/snake-head-up.png", "/snake/space-snake/snake-head-down.png"},
            {"/snake/ninja-snake/snake-body.png", "/snake/ninja-snake/snake-head-right.png", "/snake/ninja-snake/snake-head-left.png", "/snake/ninja-snake/snake-head-up.png", "/snake/ninja-snake/snake-head-down.png"},
    };
    public static final String[] FOODS_IMAGE = new String[]{"/food/food-apple.png", "/food/food-banana.png", "/food/food-blueberry.png",
            "/food/food-cherry.png", "/food/food-durian.png", "/food/food-grape.png", "/food/food-grapefruit.png", "/food/food-kiwi.png",
            "/food/food-lemon.png", "/food/food-litchi.png", "/food/food-mango.png", "/food/food-orange.png", "/food/food-peach.png",
            "/food/food-pear.png", "/food/food-pineapple.png", "/food/food-pitaya.png", "/food/food-strawberry.png", "/food/food-watermelon.png",
            "/food/cheese.png", "/food/crab.png", "/food/dish1.png", "/food/dish2.png", "/food/fish1.png","/food/fish2.png", "/food/fish3.png",
            "/food/ice-cream.png", "/food/juice.png", "/food/meat1.png", "/food/meat2.png", "/food/meat3.png", "/food/meat4.png","/food/meat5.png",
            "/food/pizza.png", "/food/pot1.png", "/food/pot2.png", "/food/pot3.png","/food/beverage.png", "/food/bowl.png", "/food/bread1.png",
            "/food/burger.png", "/food/chocolate1.png", "/food/hot-dog.png", "/food/meat6.png", "/food/pizza2.png", "/food/pizza3.png","/food/sushi.png",};

    public static final String[] GEM_IMAGES = new String[]{"/stone/green-gem.png", "/stone/purple-gem.png"};
    public static final String[] METEOR_IMAGES = new String[]{"/stone/yellow.png", "/stone/orange.png", "/stone/blue.png"};

    // Preload all images
    public static void preloadImages() {
        for (String[] snakeImages : SNAKE_IMAGES) {
            for (String snakeImage : snakeImages) {
                loadImage(snakeImage);
            }
        }

        for (String backgroundImage : BACKGROUND_IMAGES) loadImage(backgroundImage);
        for (String componentImage : COMPONENT_IMAGES) loadImage(componentImage);
        for (String foodImage : FOODS_IMAGE) loadImage(foodImage);
        for (String gemImage : GEM_IMAGES) loadImage(gemImage);
        for (String meteorImage : METEOR_IMAGES) loadImage(meteorImage);

        loadImage("/stone/coin.png");
    }

    /**
     * Loads an image from the specified path and adds it to the image cache.
     *
     * @param path The path to the image.
     */
    private static void loadImage(String path) {
        try {
            Image image = new Image(Objects.requireNonNull(ImageLoader.class.getResource(path)).toURI().toString());
            imageCache.put(path, image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + path + e.getMessage());
        }
    }

    /**
     * Returns the image from the image cache.
     *
     * @param path The path to the image.
     * @return The image.
     */
    public static Image getImage(String path) {
        return imageCache.get(path);
    }
}
