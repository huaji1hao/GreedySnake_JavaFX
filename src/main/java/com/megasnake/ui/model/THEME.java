package com.megasnake.ui.model;

public enum THEME {
    BLUE("blue_ship.png", 0),
    GREEN("green_ship.png", 1),
    ORANGE("orange_ship.png", 2),
    RED("red_ship.png", 3);

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
