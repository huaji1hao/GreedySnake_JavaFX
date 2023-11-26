package com.megasnake.utils;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static final Map<String, Image> imageCache = new HashMap<>();

    public static final String[] FOODS_IMAGE = new String[]{"/food/food-apple.png", "/food/food-banana.png", "/food/food-blueberry.png",
            "/food/food-cherry.png", "/food/food-durian.png", "/food/food-grape.png", "/food/food-grapefruit.png", "/food/food-kiwi.png",
            "/food/food-lemon.png", "/food/food-litchi.png", "/food/food-mango.png", "/food/food-orange.png", "/food/food-peach.png",
            "/food/food-pear.png", "/food/food-pineapple.png", "/food/food-pitaya.png", "/food/food-strawberry.png", "/food/food-watermelon.png"};

    private static final String[][] SNAKE_IMAGES = {
            {"/snake/plain-snake/snake-body.png", "/snake/plain-snake/snake-head-right.png", "/snake/plain-snake/snake-head-left.png", "/snake/plain-snake/snake-head-up.png", "/snake/plain-snake/snake-head-down.png"},
            {"/snake/rabbit-snake/snake-body.png", "/snake/rabbit-snake/snake-head-right.png", "/snake/rabbit-snake/snake-head-left.png", "/snake/rabbit-snake/snake-head-up.png", "/snake/rabbit-snake/snake-head-down.png"},
            {"/snake/ghost-snake/snake-body.png", "/snake/ghost-snake/snake-head-right.png", "/snake/ghost-snake/snake-head-left.png", "/snake/ghost-snake/snake-head-up.png", "/snake/ghost-snake/snake-head-down.png"},
            {"/snake/space-snake/snake-body.png", "/snake/space-snake/snake-head-right.png", "/snake/space-snake/snake-head-left.png", "/snake/space-snake/snake-head-up.png", "/snake/space-snake/snake-head-down.png"},
            {"/snake/ninjia-snake/snake-body.png", "/snake/ninjia-snake/snake-head-right.png", "/snake/ninjia-snake/snake-head-left.png", "/snake/ninjia-snake/snake-head-up.png", "/snake/ninjia-snake/snake-head-down.png"},

    };

    public static void preloadImages() {
        for (String foodImage : FOODS_IMAGE) {
            loadImage(foodImage);
        }

        for (String[] snakeImages : SNAKE_IMAGES) {
            for (String snakeImage : snakeImages) {
                loadImage(snakeImage);
            }
        }

        loadImage("/background/candy_background.png");
        loadImage("/background/lava_background.png");

    }

    private static void loadImage(String path) {
        try {
            Image image = new Image(ImageLoader.class.getResource(path).toURI().toString());
            imageCache.put(path, image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + path + e.getMessage());
        }
    }

    public static Image getImage(String path) {
        return imageCache.get(path);
    }
}
