package com.megasnake.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpeedControllerTest {

    private SpeedController speedController;

    @BeforeEach
    void setUp() {
        speedController = new SpeedController();
    }

    @Test
    void testInitialSpeedLevel() {
        assertEquals(2, speedController.getSpeedLevel());
    }

    @Test
    void testUpdateFrame() {
        assertEquals(0.875, speedController.getFrameRate());
        speedController.updateFrame();
        assertEquals(0, speedController.getFrameRate());
    }

    @Test
    void testIsMoveFrame() {
        assertTrue(speedController.isMoveFrame());
        for (int i = 0; i < 7; i++) {
            speedController.updateFrame();
            assertFalse(speedController.isMoveFrame());
        }
        speedController.updateFrame();
        assertTrue(speedController.isMoveFrame());
    }

    @Test
    void testSpeedUp() {
        speedController.speedUp();
        assertEquals(3, speedController.getSpeedLevel());
    }

    @Test
    void testSpeedDown() {
        speedController.speedDown();
        assertEquals(1, speedController.getSpeedLevel());
    }

    @Test
    void testGetSpeedLevel() {
        assertEquals(2, speedController.getSpeedLevel());
    }

    @Test
    void testGetFrameRate() {
        assertEquals(0.875, speedController.getFrameRate());
    }
}
