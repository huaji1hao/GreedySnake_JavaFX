package com.megasnake.ui.model;

public enum THEME {
    BLUE("grass_background_small.png", 0),
    GREEN("candy_background_small.png", 1),
    ORANGE("lava_background_small.jpg", 2 ),
    RED("space_background_small.jpg", 3);

    private String urlShip;

    private int difficulty;
    private THEME(String urlShip, int difficulty) {
        this.urlShip = urlShip;
        this.difficulty = difficulty;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

}
