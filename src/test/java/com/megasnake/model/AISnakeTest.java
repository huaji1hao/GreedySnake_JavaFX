package com.megasnake.model;

import com.megasnake.utils.audio.MusicPlayer;
import com.megasnake.controller.SpeedController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.megasnake.TestHelperFunction.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is used to test the AISnake class.
 * 
 * @Author Junfeng ZHU
 */
class AISnakeTest {
    private AISnake aiSnake;
    private Food[] foods;
    private MySnake mySnake;
    private MockedStatic<MusicPlayer> mockedMusicPlayer;

    @BeforeEach
    void setUp() {
        foods = new Food[1];
        mySnake = new MySnake();
        aiSnake = new AISnake(foods);

        // set the initial position of the snake
        List<Point> fakeSnakeBody = new ArrayList<>(Arrays.asList(new Point(5, 10), new Point(5, 9)));

        mockedMusicPlayer = Mockito.mockStatic(MusicPlayer.class);
        setPrivateField(aiSnake, "snakeHead", fakeSnakeBody.get(0));
        setPrivateField(aiSnake, "snakeBody", fakeSnakeBody);
        setPrivateField(mySnake, "score", 10);
        setPrivateField(aiSnake, "score", 10);
    }

    @Test
    void testMoveTowardsFood() {
        // set food position
        Food food = new Food();
        setPrivateField(food, "foodX", 6);
        setPrivateField(food, "foodY", 11);
        foods[0] = food;

        aiSnake.move();

        // verify snake head position
        Point snakeHead = aiSnake.getSnakeHead();
        assertTrue(snakeHead.getX() > 5 || snakeHead.getY() > 10);
    }

    @Test
    void testEatFood() {
        // get the original speed level
        SpeedController aiSnakeSpeedController = (SpeedController) getPrivateField(aiSnake, "speedController");
        int originalAIScore = aiSnake.getScore();
        // set food position which is the same as the snake head
        Food food = new Food();
        setPrivateField(food, "foodX", 5);
        setPrivateField(food, "foodY", 10);

        aiSnake.eatFood(food, mySnake);

        // verify snake gets score
        assertEquals(aiSnake.getScore(), originalAIScore + 5 * aiSnakeSpeedController.getSpeedLevel());
        // verify snake may grow
        assertTrue(aiSnake.getBodySize() >= 2);
        // verify the music is played
        mockedMusicPlayer.verify(() -> MusicPlayer.playMusic("/audio/laugh.mp3"));
    }

    @Test
    void testHitMySnake() {
        // get the original speed level
        SpeedController aiSnakeSpeedController = (SpeedController) getPrivateField(aiSnake, "speedController");
        int originalAIScore = aiSnake.getScore();
        int originalMyScore = mySnake.getScore();

        aiSnake.hitMySnake(mySnake);

        // verify the loss of score
        assertEquals(aiSnake.getScore(), originalAIScore - aiSnakeSpeedController.getSpeedLevel());
        assertEquals(mySnake.getScore(), originalMyScore - aiSnakeSpeedController.getSpeedLevel());
        // verify the music is played
        mockedMusicPlayer.verify(() -> MusicPlayer.playMusic("/audio/hit.mp3"));
    }

    @AfterEach
    void tearDown() {
        mockedMusicPlayer.close();
    }

}
