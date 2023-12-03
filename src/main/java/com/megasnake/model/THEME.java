package com.megasnake.model;

/**
 * Enumerates the different themes available in the game.
 *
 * @author Junfeng ZHU
 */
public enum THEME {

    /**
     * The different themes available in the game.
     * <p>
     * Each theme has a URL to the background image, a level and a level text.
     * The level is used to determine the difficulty of the theme.
     */
    GRASS("/background/grass_background_small.png", 0, "Easy"),
    CANDY("/background/candy_background_small.png", 1, "Medium"),
    LAVA("/background/lava_background_small.jpg", 2 , "Hard"),
    SPACE("/background/space_background_small.jpg", 3, "Hell");

    private final String urlTheme;
    private final int level;
    private final String levelText;
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int HELL = 3;

    /**
     * Constructs a new theme.
     *
     * @param urlTheme The URL to the background image.
     * @param level The level of the theme.
     * @param levelText The level text of the theme.
     */
    THEME(String urlTheme, int level, String levelText) {
        this.urlTheme = urlTheme;
        this.level = level;
        this.levelText = levelText;
    }

    /**
     * Returns the URL to the background image.
     *
     * @return The URL to the background image.
     */
    public String getUrl() {
        return this.urlTheme;
    }

    /**
     * Returns the level of the theme.
     *
     * @return The level of the theme.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the level text of the theme.
     *
     * @return The level text of the theme.
     */
    public String getLevelText() {
        return this.levelText;
    }

}
