package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.megasnake.TestHelperFunction.*;
import static com.megasnake.controller.GameController.COLUMNS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class is used to test the Coin class.
 * The test for the gem class should be similar to the test for the coin class.
 * So I'm not going to write it.
 * 
 * @Author Junfeng ZHU
 */
class CoinTest {
    private Coin coin;
    private SpeedController mockSpeedController;

    @BeforeEach
    void setUp(){
        mockSpeedController = mock(SpeedController.class);
        coin = new Coin();
        setPrivateField(coin, "speedController", mockSpeedController);
    }

    @Test
    void testMoveWhenSpeedControllerAllowsMovement(){
        when(mockSpeedController.isMoveFrame()).thenReturn(true);

        setPrivateField(coin, "x", 5); // Middle of the screen
        setPrivateField(coin, "y", 0); // Top of the screen
        setPrivateField(coin, "horizontalDirection", Coin.HORIZONTAL_RIGHT);

        coin.move();

        assertEquals(1, getPrivateField(coin, "y"), "Y coordinate should increment after move");
        assertEquals(6, getPrivateField(coin, "x"), "X coordinate should increment (move right)");
        assertTrue((double) getPrivateField(coin, "rotationAngle") > 0, "Rotation angle should increase");
    }

    @Test
    void testMoveWhenReachingEdgeOfScreen(){
        when(mockSpeedController.isMoveFrame()).thenReturn(true);

        setPrivateField(coin, "x", COLUMNS - 1); // Right edge of the screen
        setPrivateField(coin, "y", 0); // Top of the screen
        setPrivateField(coin, "horizontalDirection", Coin.HORIZONTAL_RIGHT);

        coin.move();

        assertEquals(Coin.HORIZONTAL_LEFT, getPrivateField(coin, "horizontalDirection"), "Direction should reverse at screen edge");
    }

}
