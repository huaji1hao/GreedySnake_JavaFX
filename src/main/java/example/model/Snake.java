package example.model;

import example.movable;
import example.utils.ImageUtil;
import example.utils.GameUtil;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Snake extends SnakeObject implements movable {
    private int speedXY;
    private int length;
    private int num;
    public int score = 0;

    private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake-head-right");
    public static List<Point> bodyPoints = new LinkedList<>();
    private static BufferedImage newImgSnakeHead;
    private boolean up, down, left, right = true;

    public Snake(int x, int y) {
        this.alive = true;
        this.x = x;
        this.y = y;
        this.image = ImageUtil.images.get("snake-body");
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        this.speedXY = 5;
        this.length = 1;
        this.num = width / speedXY;
        newImgSnakeHead = IMG_SNAKE_HEAD;
    }

    public int getLength() {
        return length;
    }

    public void changeLength(int length) {
        this.length = length;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (!down) {
                    up = true;
                    down = left = right = false;
                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!up) {
                    down = true;
                    up = left = right = false;
                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!right) {
                    left = true;
                    up = down = right = false;
                    newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!left) {
                    right = true;
                    up = down = left = false;
                    newImgSnakeHead = IMG_SNAKE_HEAD;
                }
                break;
            default:
                break;
        }
    }

    public void move() {
        if (up) {
            y -= speedXY;
        } else if (down) {
            y += speedXY;
        } else if (left) {
            x -= speedXY;
        } else if (right) {
            x += speedXY;
        }
    }

    @Override
    public void draw(Graphics g) {
        outOfBounds();
        eatBody();

        bodyPoints.add(new Point(x, y));

        if (bodyPoints.size() > (length * num)) {
            bodyPoints.remove(0);
        }

        g.drawImage(newImgSnakeHead, x, y, null);
        drawBody(g);
        move();
    }

    private void eatBody() {
        for (int i = 0; i < bodyPoints.size() - 1; i++) {
            Point point = bodyPoints.get(i);
            if (x == point.x && y == point.y) {
                alive = false;
                break;
            }
        }
    }

    private void drawBody(Graphics g) {
        for (int i = 0; i < bodyPoints.size() - 1; i++) {
            Point point = bodyPoints.get(i);
            g.drawImage(this.image, point.x, point.y, null);
        }
    }

    private void outOfBounds() {
        if (x < 0 || x >= (870 - width) || y < 40 || y >= (560 - height)) {
            alive = false;
        }
    }
}
