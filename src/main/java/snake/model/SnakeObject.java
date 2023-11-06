package snake.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public abstract class SnakeObject {
    protected int x;
    protected int y;
    protected Image image;
    protected int width;
    protected int height;
    protected boolean alive;

    public abstract void draw(GraphicsContext gc);

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle2D getRectangle2D() {
        return new Rectangle2D(x, y, width, height);
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
