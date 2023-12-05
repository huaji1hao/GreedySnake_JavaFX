package com.megasnake.controller;

import com.megasnake.model.*;
import com.megasnake.utils.ImageLoader;
import com.megasnake.utils.audio.*;
import com.megasnake.utils.scoreboard.UserWriter;
import com.megasnake.utils.KeyEventHandler;
import com.megasnake.view.component.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import static com.megasnake.model.THEME.*;
import static com.megasnake.utils.KeyEventHandler.RIGHT;

/**
 * Controller class for the MegaSnake game.
 * @Project MegaSnake
 * @author Sigurður Sigurðardóttir
 * @author Junfeng ZHU-modified
 */
public class SnakeGameController extends GameController{
    private Food[] foods;
    private MySnake mySnake;
    private KeyEventHandler keyEventHandler;
    private SnakeTextField usernameInput;
    private int level;
    Meteor meteor;
    Gem gem;
    Coin coin;
    AISnake aiSnake;

    // Default food number is 2
    private static int foodNum = 2;
    // Default playable feature is true
    private static boolean isPlayableFeature = true;
    // Default AI snake is true
    private static boolean isAISnake = true;

    /**
     * Initializes the controller.
     *
     * @param menuStage The stage for the main menu which will be hidden when the game starts.
     */
    public SnakeGameController(Stage menuStage) {
        super(menuStage);
        initializeSnakeGame();
    }

    /**
     * Ready to start the game and set the level of the game.
     * The menu stage will be hidden and the game stage will be shown.
     *
     * @param level The level of the game.
     */
    public void runSnakeGame(int level) {
        mySnake.setLevel(level);
        this.level = level;
        this.menuStage.hide();
        gameStage.show();
        run();
    }

    /**
     * Starts the game!
     * The game timer will infinitely call the main logic of the game every 32 milliseconds
     */
    @Override
    protected void run() {
        chooseBackgroundMusic();
        for (Food food : foods) food.generateFood(mySnake);
        scene.setOnKeyPressed(keyEventHandler);
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    /**
     * Chooses the background music based on the level of the game.
     */
    private void chooseBackgroundMusic() {
        switch (level) {
            case MEDIUM -> BackgroundMusicPlayer.repeatMusic("/audio/quqidao.mp3");
            case HARD -> BackgroundMusicPlayer.repeatMusic("/audio/huoshandao.mp3");
            case HELL -> BackgroundMusicPlayer.repeatMusic("/audio/shendian.mp3");
            default -> BackgroundMusicPlayer.repeatMusic("/audio/frogger.mp3");
        }
    }

    /**
     * Initializes the game.
     * Sets up the game stage and the game timer.
     * Sets up the foods, the snake, the meteor, the gem, the coin and the AI snake.
     */
    private void initializeSnakeGame() {
        gameStage.setTitle("MegaSnake");
        gameStage.getIcons().add(ImageLoader.getImage("/component/little-logo.png"));
        gameStage.setResizable(false);

        // Stop music when the window is closed
        gameStage.setOnCloseRequest((WindowEvent we) -> {
            BackgroundMusicPlayer.stopMusic();
            Platform.exit();
            System.exit(0);
        });

        usernameInput = new SnakeTextField();

        // Initialize the food array
        foods = new Food[foodNum];
        for (int i = 0; i < foods.length; i++) foods[i] = new Food();

        mySnake = new MySnake();
        meteor = new Meteor();
        gem = new Gem();
        coin = new Coin();
        aiSnake = new AISnake(foods);

        // The game timer will call the main logic of the game every 32 milliseconds
        gameTimer = new Timeline(new KeyFrame(Duration.millis(32), e -> mainLogic(mySnake, foods, meteor, gem, coin, aiSnake)));
        keyEventHandler = new KeyEventHandler(RIGHT, gameTimer, gc);
    }

    /**
     * The main logic of the game.
     * Moves the snake, the meteor, the gem and the coin.
     * Checks if the snake is hit by the meteor, touches the gem or eats the coin.
     * Draws all the components of the game.
     * Checks if the game is over.
     *
     * @param mySnake The snake controlled by the player.
     * @param foods The array of foods.
     * @param meteor The meteor.
     * @param gem The gem.
     * @param coin The coin.
     * @param aiSnake The AI snake.
     * @author Sigurður Sigurðardóttir
     * @author Junfeng ZHU-modified
     */
    private void mainLogic(MySnake mySnake, Food[] foods, Meteor meteor, Gem gem, Coin coin, AISnake aiSnake) {
        if (isGameOver) {
            gameOver();
            return;
        }

        mySnake.move();
        for (Food food : foods) mySnake.eatFood(food);

        /*
         * If the playable feature is enabled,
         * the meteor, the gem and the coin will move and the snake will be affected by them.
         */
        if(isPlayableFeature){
            meteor.move();
            gem.move();
            coin.move();
            mySnake.hitByMeteor(meteor);
            mySnake.touchGem(gem);
            mySnake.eatCoin(coin);
        }

        /*
         * If the AI snake is enabled,
         * the AI snake will move and affect the snake of player and the foods.
         */
        if(isAISnake){
            aiSnake.move();
            aiSnake.hitMySnake(mySnake);
            for(Food food : foods) aiSnake.eatFood(food, mySnake);
        }

        gameview.drawAll(mySnake, foods, level, meteor, gem, coin, aiSnake);

        isGameOver(mySnake);
    }

    /**
     * Checks if the game is over.
     * The game is over if the snake is destroyed or the snake goes out of the game window.
     *
     * @param mySnake The snake controlled by the player.
     */
    private void isGameOver(MySnake mySnake) {
        // when the snake is destroyed
        if (mySnake.getBodySize() <= 1) {
            isGameOver = true;
            return;
        }

        // when the snake goes out of the game window
        Point snakeHead = mySnake.getSnakeHead();
        if (snakeHead.getX() < 0 || snakeHead.getY() < 0 || snakeHead.getX() * SQUARE_SIZE >= WIDTH || snakeHead.getY() * SQUARE_SIZE >= HEIGHT) {
            isGameOver = true;
            return;
        }

        // when the snake hits the body of itself
        for (int i = 1; i < mySnake.getBodySize(); i++) {
            if (snakeHead.getX() == mySnake.getBodyPart(i).getX() && snakeHead.getY() == mySnake.getBodyPart(i).getY()) {
                isGameOver = true;
                return;
            }
        }

    }

    /**
     * Shows the game over scene.
     * Stops the game timer and the background music.
     * Plays the game over music.
     * <p>
     * Creates a new scene when the game is over.
     * The new scene contains a label to show the score of the player and a text field to enter the username.
     * The new scene also contains two buttons to restart the game and go back to the main menu.
     */
    @Override
    protected void gameOver() {
        gameTimer.stop();
        BackgroundMusicPlayer.stopMusic();
        gameview.drawGameOver();
        SoundEffectPlayer.playMusic("/audio/game-over.mp3");

        // Pause for 3 seconds for waiting the game over music to finish
        PauseTransition pause = new PauseTransition(Duration.seconds(3));

        pause.setOnFinished(event -> {
            // create a new scene when the game is over
            SnakeSubScene backSubScene = new SnakeSubScene();
            root.getChildren().add(backSubScene);
            backSubScene.moveSubSceneInGame();

            // show the score of the player and a text field to enter the username if the user wants to save the score
            CustomLabel label = new CustomLabel("Your score is " + mySnake.getScore(), 23);
            label.setPos(180, 100);
            usernameInput.setPos(150, 180);

            backSubScene.getPane().getChildren().add(label);
            backSubScene.getPane().getChildren().add(usernameInput);
            backSubScene.getPane().getChildren().add(createButtonToRestart());
            backSubScene.getPane().getChildren().add(createButtonToBack());
        });

        pause.play();
    }

    /**
     * Creates a button to go back to the main menu.
     *
     * @return The button to go back to the main menu.
     */
    private SnakeButton createButtonToBack() {
        SnakeButton backButton = new SnakeButton("Go Back");
        backButton.setButtonStyleQing();

        backButton.setLayoutX(350);
        backButton.setLayoutY(280);

        backButton.setOnAction(event -> {
            // save the score of the player if the user enters a username
            String username = usernameInput.getTextValue().trim();
            if (username.isEmpty()) {
                showAlert("You did not enter a username, so this record will not be retained.");
            } else {
                // write the score to the file
                User newUser = new User(username, mySnake.getScore());
                UserWriter.writeUserToFile(newUser);
            }
            gameStage.close();
            menuStage.show();
            BackgroundMusicPlayer.repeatMusic("/audio/ui-background.mp3");
        });


        return backButton;
    }

    /**
     * Creates a button to restart the game.
     *
     * @return The button to restart the game.
     */
    private SnakeButton createButtonToRestart() {
        SnakeButton restartButton = new SnakeButton("Restart");
        restartButton.setButtonStyleQing();

        restartButton.setLayoutX(60);
        restartButton.setLayoutY(280);

        restartButton.setOnAction(event -> {
            // close the original game stage to avoid creating multiple game stages
            gameStage.close();
            // create a new game stage but keep the menu stage
            SnakeGameController newGameController = new SnakeGameController(menuStage);
            newGameController.runSnakeGame(level);
        });

        return restartButton;
    }

    /**
     * Get whether the playable feature like the meteor, the gem and the coin is enabled.
     *
     * @return Whether the playable feature is enabled.
     */
    public static boolean getIsPlayableFeature(){
        return isPlayableFeature;
    }

    /**
     * Set whether the playable feature like the meteor, the gem and the coin is enabled.
     *
     * @param playableFeature The playable feature.
     */
    public static void setPlayableFeature(boolean playableFeature){
        isPlayableFeature = playableFeature;
    }

    /**
     * Get whether the AI snake is enabled.
     *
     * @return Whether the AI snake is enabled.
     */
    public static boolean getIsAISnake(){
        return isAISnake;
    }

    /**
     * Set whether the AI snake is enabled.
     *
     * @param aiSnake The AI snake.
     */
    public static void setAISnake(boolean aiSnake){
        isAISnake = aiSnake;
    }

    /**
     * Set the number of food.
     *
     * @param num The number of food.
     */
    public static void setFoodNum(int num){
        if(num > 0 && num < 6) foodNum = num;
    }
}
