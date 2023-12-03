package com.megasnake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is used to test the Point class.
 * 
 * @author Junfeng ZHU
 */
class PointTest {

    @Test
    void testPointConstructorAndGetters() {
        int x = 5;
        int y = 10;
        Point point = new Point(x, y);

        assertEquals(x, point.getX(), "X coordinate should match constructor argument");
        assertEquals(y, point.getY(), "Y coordinate should match constructor argument");
    }

    @Test
    void testSetX() {
        int initialX = 5;
        int newX = 15;
        Point point = new Point(initialX, 10);

        point.setX(newX);
        assertEquals(newX, point.getX(), "X coordinate should be updated");
    }

    @Test
    void testSetY() {
        int initialY = 10;
        int newY = 20;
        Point point = new Point(5, initialY);

        point.setY(newY);
        assertEquals(newY, point.getY(), "Y coordinate should be updated");
    }
}
