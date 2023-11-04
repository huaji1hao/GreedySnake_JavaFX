package com.simplesnake.ui;


import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements KeyListener {
    public GamePanel() {
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        new GameThread(this).start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Implementation
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation
    }

    private class GameThread extends Thread {
        private final JPanel panel;

        public GameThread(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            while (true) {
                panel.repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
