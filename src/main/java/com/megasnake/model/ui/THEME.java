package com.megasnake.model.ui;

public enum THEME {
    BLUE("/grass_background_small.png", 0, "Easy"),
    GREEN("/candy_background_small.png", 1, "Medium"),
    ORANGE("/lava_background_small.jpg", 2 , "Hard"),
    RED("/space_background_small.jpg", 3, "Hell");

    private String urlShip;

    private int difficulty;
    private String level;
    private THEME(String urlShip, int difficulty, String level) {
        this.urlShip = urlShip;
        this.difficulty = difficulty;
        this.level = level;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public String getLevel() {
        return this.level;
    }

}
