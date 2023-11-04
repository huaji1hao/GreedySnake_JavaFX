package example.controller;

import example.ui.GamePanel;
import example.ui.GameWindow;
import example.model.Food;
import example.model.Snake;
import example.utils.ImageUtil;
import example.audio.MusicPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class GameController extends GamePanel {

    private Snake mySnake = new Snake(100, 100); // x , y
    private Food food = new Food();
    private Image background = ImageUtil.images.get("UI-background");
    private Image fail = ImageUtil.images.get("game-scene-01");

    public GameController() {
        super();
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        mySnake.keyPressed(e);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        // Decide the state of the game.
        if (mySnake.isAlive()) {
            mySnake.draw(g);
            if (food.isAlive()) {
                food.draw(g);
                food.eaten(mySnake);
            } else {
                food = new Food();
            }
        } else {
            g.drawImage(fail, 0, 0, null);
        }
        drawScore(g);
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.setColor(Color.MAGENTA);
        g.drawString("SCORE : " + mySnake.score, 20, 40);
    }

    public static void startGame() {
        new GameWindow();
        MusicPlayer.playMusic("/frogger.mp3");
    }
}
