package com.megasnake.model;

import com.megasnake.controller.SpeedController;
import com.megasnake.utils.KeyEventHandler;
import com.megasnake.utils.audio.SoundEffectPlayer;
import javafx.animation.Animation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static com.megasnake.TestHelperFunction.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
/**
 * This class is used to test the MySnake class.
 * 
 * @author Junfeng ZHU
 */
class MySnakeTest {
    private MySnake mySnake;
    private Food food;
    private Meteor meteor;
    private Gem gem;
    private Coin coin;
    private MockedStatic<SoundEffectPlayer> mockedMusicPlayer;

    @BeforeEach
    void setUp(){
        // Mock static methods of SoundEffectPlayer
        mockedMusicPlayer = mockStatic(SoundEffectPlayer.class);
        // Stub playMusic() method
        mockedMusicPlayer.when(() -> SoundEffectPlayer.playMusic(anyString())).thenAnswer(invocation -> null);

        mySnake = new MySnake();
        food = new Food();
        meteor = new Meteor();
        gem = new Gem();
        coin = new Coin();

        // Set initial position of food, meteor, gem, and coin
        setPrivateField(food, "foodX", 5);
        setPrivateField(food, "foodY", 10);
        setPrivateField(meteor, "x", 5);
        setPrivateField(meteor, "y", 10);
        setPrivateField(gem, "x", 5);
        setPrivateField(gem, "y", 10);
        setPrivateField(coin, "x", 5);
        setPrivateField(coin, "y", 10);
        setPrivateField(mySnake, "score", 10);
    }

    @Test
    void testEatFood(){
        // get the original speed level, body size, and score
        SpeedController mySnakeSpeedController = (SpeedController) getPrivateField(mySnake, "speedController");
        int originalBodySize = mySnake.getBodySize();
        int originalScore = mySnake.getScore();

        mySnake.eatFood(food);

        // The snake should grow its tail and get more score after eating the food
        assertEquals(mySnake.getBodySize(), originalBodySize + 1);
        assertEquals(mySnake.getScore(), originalScore + 5 * mySnakeSpeedController.getSpeedLevel());
    }

    @Test
    void testHitByMeteor(){
        // get the original speed level, body size, and score
        SpeedController mySnakeSpeedController = (SpeedController) getPrivateField(mySnake, "speedController");
        int originalSpeedLevel = mySnakeSpeedController.getSpeedLevel();
        int originalBodySize = mySnake.getBodySize();
        int originalScore = mySnake.getScore();

        mySnake.hitByMeteor(meteor);

        // The snake should lose its tail after hitting the meteor
        assertEquals(mySnake.getBodySize(), originalBodySize - 1);
        // The speed level may decrease after hitting the meteor
        assertTrue(mySnakeSpeedController.getSpeedLevel() <= originalSpeedLevel);
        // The score should decrease after hitting the meteor
        assertEquals(mySnake.getScore(), originalScore - 4 * mySnakeSpeedController.getSpeedLevel());
    }

    @Test
    void testTouchGem(){
        // get the original speed level and score
        SpeedController mySnakeSpeedController = (SpeedController) getPrivateField(mySnake, "speedController");
        int originalSpeedLevel = mySnakeSpeedController.getSpeedLevel();
        int originalScore = mySnake.getScore();

        mySnake.touchGem(gem);

        // The speed level and score should increase after touching the gem
        assertEquals(mySnakeSpeedController.getSpeedLevel(), originalSpeedLevel + 1);
        assertEquals(mySnake.getScore(), originalScore + 8 * mySnakeSpeedController.getSpeedLevel());
    }

    @Test
    void testEatCoin(){
        // get the original speed level and score
        SpeedController mySnakeSpeedController = (SpeedController) getPrivateField(mySnake, "speedController");
        int originalScore = mySnake.getScore();

        mySnake.eatCoin(coin);

        // The score should increase after eating the coin
        assertEquals(mySnake.getScore(), originalScore + 6 * mySnakeSpeedController.getSpeedLevel());
    }

    @Test
    void testMoveRight() {
        KeyEventHandler keyEventHandler = new KeyEventHandler(KeyEventHandler.UP, mock(Animation.class), null);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        int originalHeadX = mySnake.getSnakeHead().getX();
        int originalHeadY = mySnake.getSnakeHead().getY();
        mySnake.move();

        assertEquals(originalHeadX + 1, mySnake.getSnakeHead().getX());
        assertEquals(originalHeadY, mySnake.getSnakeHead().getY());
    }

    @Test
    void testMoveLeft() {
        KeyEventHandler keyEventHandler = new KeyEventHandler(KeyEventHandler.UP, mock(Animation.class), null);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        int originalHeadX = mySnake.getSnakeHead().getX();
        int originalHeadY = mySnake.getSnakeHead().getY();
        mySnake.move();

        assertEquals(originalHeadX - 1, mySnake.getSnakeHead().getX());
        assertEquals(originalHeadY, mySnake.getSnakeHead().getY());
    }

    @Test
    void testMoveUp() {
        KeyEventHandler keyEventHandler = new KeyEventHandler(KeyEventHandler.RIGHT, mock(Animation.class), null);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        int originalHeadX = mySnake.getSnakeHead().getX();
        int originalHeadY = mySnake.getSnakeHead().getY();
        mySnake.move();

        assertEquals(originalHeadX, mySnake.getSnakeHead().getX());
        assertEquals(originalHeadY - 1, mySnake.getSnakeHead().getY());
    }

    @Test
    void testMoveDown() {
        KeyEventHandler keyEventHandler = new KeyEventHandler(KeyEventHandler.RIGHT, mock(Animation.class), null);
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        int originalHeadX = mySnake.getSnakeHead().getX();
        int originalHeadY = mySnake.getSnakeHead().getY();
        mySnake.move();

        assertEquals(originalHeadX, mySnake.getSnakeHead().getX());
        assertEquals(originalHeadY + 1, mySnake.getSnakeHead().getY());
    }

    @Test
    void testMoveReversely() {
        // The current direction is RIGHT
        KeyEventHandler keyEventHandler = new KeyEventHandler(KeyEventHandler.RIGHT, mock(Animation.class), null);
        // The user presses the LEFT key
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        keyEventHandler.handle(keyEvent);

        int originalHeadX = mySnake.getSnakeHead().getX();
        int originalHeadY = mySnake.getSnakeHead().getY();
        mySnake.move();

        // The snake should not move reversely so the direction should still be RIGHT
        assertEquals(originalHeadX + 1, mySnake.getSnakeHead().getX());
        assertEquals(originalHeadY, mySnake.getSnakeHead().getY());
    }


    @Test
    void testSetLevel(){
        mySnake.setLevel(2);
        assertEquals(2, getPrivateField(mySnake, "level"));
    }

    @AfterEach
    void tearDown() {
        // Close the mocked static block after the test
        mockedMusicPlayer.close();
    }
}
