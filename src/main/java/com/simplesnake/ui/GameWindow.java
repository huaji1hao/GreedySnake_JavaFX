package com.simplesnake.ui;

import com.simplesnake.controller.GameController;

import javax.swing.JFrame;
import java.awt.Toolkit;

public class GameWindow extends JFrame {
    private static final long serialVersionUID = -3149926831770554380L;

    public GameWindow() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(GameWindow.class.getResource("/snake-logo.png")));
        this.setTitle("Snakee Yipee");
        this.setSize(870, 560);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new GameController());
        this.setVisible(true);
    }
}
