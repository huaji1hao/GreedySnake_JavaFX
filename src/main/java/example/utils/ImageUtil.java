package example.utils;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing game images.
 */
public class ImageUtil {

    public static final Map<String, Image> images = new HashMap<>();

    static {
        loadImages();
    }

    /**
     * Loads all game images into the images map.
     */
    private static void loadImages() {
        // Snake images
        loadImage("snake-head-right", "snake-head-right.png");
        loadImage("snake-body", "snake-body.png");

        // Food images
        for (int i = 0; i <= 16; i++) {
            loadImage(String.valueOf(i), "food-" + getFoodName(i) + ".png");
        }

        // UI images
        loadImage("UI-background", "UI-background.png");
        loadImage("game-scene-01", "game-scene-01.jpg");
    }

    /**
     * Loads an image and puts it into the images map.
     *
     * @param key        The key associated with the image.
     * @param imagePath  The path to the image file.
     */
    private static void loadImage(String key, String imagePath) {
        Image image = GameUtil.getImage(imagePath);
        if (image != null) {
            images.put(key, image);
        } else {
            System.err.println("Failed to load image for key: " + key);
        }
    }

    /**
     * Returns the name of the food based on its index.
     *
     * @param index The index of the food.
     * @return The name of the food.
     */
    private static String getFoodName(int index) {
        switch (index) {
            case 0: return "kiwi";
            case 1: return "lemon";
            case 2: return "litchi";
            case 3: return "mango";
            case 4: return "apple";
            case 5: return "banana";
            case 6: return "blueberry";
            case 7: return "cherry";
            case 8: return "durian";
            case 9: return "grape";
            case 10: return "grapefruit";
            case 11: return "peach";
            case 12: return "pear";
            case 13: return "orange";
            case 14: return "pineapple";
            case 15: return "strawberry";
            case 16: return "watermelon";
            default: return "unknown";
        }
    }
}
