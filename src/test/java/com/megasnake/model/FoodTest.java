package com.megasnake.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static com.megasnake.TestHelperFunction.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is used to test the Food class.
 * 
 * @Author Junfeng ZHU
 */
class FoodTest {

    @Test
    void testGenerateFoodNotOnSnake() throws NoSuchFieldException, IllegalAccessException {
        Food food = new Food();
        MySnake mySnake = createSnakeWithBodyParts();

        food.generateFood(mySnake);

        int foodX = (int) getPrivateField(food, "foodX");
        int foodY = (int) getPrivateField(food, "foodY");

        // Ensure that the food is not generated on the snake's body
        for (int i = 0; i < mySnake.getBodySize(); i++) {
            Point part = mySnake.getBodyPart(i);
            assertFalse(part.getX() == foodX && part.getY() == foodY, "Food should not be on the snake");
        }
    }

    private MySnake createSnakeWithBodyParts() throws NoSuchFieldException, IllegalAccessException {
        // Create a snake with predefined body parts
        MySnake mySnake = new MySnake();
        List<Point> bodyParts = new ArrayList<>();
        bodyParts.add(new Point(5, 5));
        bodyParts.add(new Point(5, 6));
        bodyParts.add(new Point(5, 7));
        // Use reflection to set the snake's body
        setPrivateField(mySnake, "snakeBody", bodyParts);
        return mySnake;
    }
}
