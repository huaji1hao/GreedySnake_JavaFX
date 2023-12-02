package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.megasnake.controller.GameController.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SnakeObjectTest {

    private TestSnakeObject testSnakeObject;
    private SpeedController mockSpeedController;

    @BeforeEach
    void setUp() {
        // Create a mock SpeedController
        mockSpeedController = mock(SpeedController.class);
        // Create an instance of the testable SnakeObject
        testSnakeObject = new TestSnakeObject(mockSpeedController);
    }

    @Test
    void testMove() {
        // Arrange: Set the mock to return true for isMoveFrame
        when(mockSpeedController.isMoveFrame()).thenReturn(true);

        // Act: Call the move method
        testSnakeObject.move();

        // Assert: Verify that the Y coordinate is incremented
        assertEquals(1, testSnakeObject.getY(), "Y coordinate should increment after move");
    }

    @Test
    void testSetRandomPosition() {
        // Act: Set a random position
        testSnakeObject.setRandomPosition();

        // Assert: Verify that the position is within the expected range
        assertTrue(testSnakeObject.getX() >= 0 && testSnakeObject.getX() < COLUMNS, "X should be within columns range");
        assertTrue(testSnakeObject.getY() <= 0, "Y should be negative or zero (above the screen)");
    }

    @Test
    void testCheckIfIsOutOfScreen() {
        // Arrange: Set the Y coordinate to a value greater than ROWS
        testSnakeObject.setY(ROWS + 1);

        // Act: Call checkIfIsOutOfScreen
        testSnakeObject.checkIfIsOutOfScreen();

        // Assert: Verify that the position is reset
        assertTrue(testSnakeObject.getY() < 0, "Y should be reset to a negative value");
    }

    // Inner class for testing purposes
    private static class TestSnakeObject extends SnakeObject {
        TestSnakeObject(SpeedController speedController) {
            this.speedController = speedController;
        }

        @Override
        public void move() {
            super.move();
        }

        // Method to set Y coordinate for testing purposes
        public void setY(int y) {
            this.y = y;
        }
    }
}
