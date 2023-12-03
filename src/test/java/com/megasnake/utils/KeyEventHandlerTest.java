package com.megasnake.utils;

import javafx.animation.Animation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * This class tests the KeyEventHandler class.
 * <p>
 * The KeyEventHandler class is responsible for handling key events.
 * <p>
 * The KeyEventHandler class is tested by simulating key events and 
 * verifying that the correct behaviour occurs.
 * 
 * @see KeyEventHandler
 * @author Junfeng ZHU
 */

class KeyEventHandlerTest {

    private KeyEventHandler keyEventHandler;
    private Animation gameTimer;

    @BeforeEach
    void setUp() {
        Canvas canvas = new Canvas(500, 500); // Dummy canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameTimer = mock(Animation.class);
        keyEventHandler = new KeyEventHandler(KeyEventHandler.RIGHT, gameTimer, gc);
    }

    @Test
    void testInitialDirection() {
        assertEquals(KeyEventHandler.RIGHT, KeyEventHandler.getCurrentDirection(), "Initial direction should be RIGHT");
    }

    @Test
    void testHandleUpArrowKey() {
        // simulate pressing the UP key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        keyEventHandler.handle(keyEvent);
        assertEquals(KeyEventHandler.UP, KeyEventHandler.getNextDirection(), "Next direction should be UP");
    }

    @Test
    void testHandleRightArrowKey() {
        // simulate pressing the RIGHT key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);
        keyEventHandler.handle(keyEvent);
        assertEquals(KeyEventHandler.RIGHT, KeyEventHandler.getNextDirection(), "Next direction should be RIGHT");
    }

    @Test
    void testHandleDownArrowKey() {
        // simulate pressing the DOWN key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);
        keyEventHandler.handle(keyEvent);
        assertEquals(KeyEventHandler.DOWN, KeyEventHandler.getNextDirection(), "Next direction should be DOWN");
    }

    @Test
    void testHandleLeftArrowKey() {
        // assume that the current direction is UP
        KeyEventHandler.setCurrentDirection(KeyEventHandler.UP);
        // simulate pressing the LEFT key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        keyEventHandler.handle(keyEvent);
        assertEquals(KeyEventHandler.LEFT, KeyEventHandler.getNextDirection(), "Next direction should be LEFT");
    }

    @Test
    void testHandleReverseArrowKey() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        keyEventHandler.handle(keyEvent);
        // the direction should not change because the snake cannot turn 180 degrees
        assertEquals(KeyEventHandler.RIGHT, KeyEventHandler.getNextDirection(), "Next direction should be RIGHT");
    }

    @Test
    void testHandleSpaceKey() {
        // Set up the game as running
        when(gameTimer.getStatus()).thenReturn(Animation.Status.RUNNING);

        // Simulate pressing the SPACE key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        // Verify that the game is paused
        verify(gameTimer).pause();
    }

}
