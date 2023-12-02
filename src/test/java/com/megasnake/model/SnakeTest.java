package com.megasnake.model;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    private TestSnake testSnake;

    @BeforeEach
    void setUp() {
        testSnake = new TestSnake();
    }

    @Test
    void testMoveBody() {
        testSnake.addBodyPart(new Point(1, 1));
        testSnake.addBodyPart(new Point(2, 2));
        testSnake.moveBody();

        assertEquals(0, testSnake.getBodyPart(1).getX());
        assertEquals(0, testSnake.getBodyPart(1).getY());
        assertEquals(1, testSnake.getBodyPart(2).getX());
        assertEquals(1, testSnake.getBodyPart(2).getY());
    }

    @Test
    void testRemoveTail() {
        testSnake.addBodyPart(new Point(1, 1));
        testSnake.addBodyPart(new Point(2, 2));
        testSnake.removeTail();

        assertEquals(2, testSnake.getBodySize());
    }

    @Test
    void testAddScore() {
        testSnake.addScore(10);
        assertEquals(10, testSnake.getScore());
    }

    @Test
    void testReduceScore() {
        testSnake.addScore(10);
        testSnake.reduceScore(5);
        assertEquals(5, testSnake.getScore());
    }

    // Inner class to test abstract Snake class
    private static class TestSnake extends Snake {
        TestSnake() {
            snakeBody = new ArrayList<>();
            snakeHead = new Point(0, 0);
            snakeBody.add(snakeHead);
        }

        void addBodyPart(Point point) {
            snakeBody.add(point);
        }

        @Override
        public void move() {
            // Do nothing for testing purposes
        }

        @Override
        public Image getHeadImage(int direction) {
            return null; // Return null for testing purposes
        }

        @Override
        public Image getBodyImage() {
            return null; // Return null for testing purposes
        }
    }
}
