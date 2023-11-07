package com.simplesnake.model;

import javafx.geometry.Rectangle2D;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class SnakeObject {
    protected int x;
    protected int y;
    protected Image image;
    protected int width;
    protected int height;
    protected boolean alive;

    public abstract void draw(Graphics g);

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    // Check if the object is alive
    public boolean isAlive() {
        return alive;
    }

    // Set the object's alive status
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // Additional common methods for snake objects can be added here
}
