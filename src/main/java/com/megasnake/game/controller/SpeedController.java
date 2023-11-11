package com.megasnake.game.controller;

public class SpeedController {
    private int slowspeed; // The larger the slowspeed, the slower the snake moves
    private int frame;

    public SpeedController() {
        this.slowspeed = 5;
        this.frame = slowspeed - 1;
    }

    public int getSpeedLevel() {
        return 6 - slowspeed;
    }
    public double getFrameRate() {
        return 1.0 * frame / slowspeed;
    }

    public void updateFrame() {
        frame = (frame + 1) % slowspeed;
    }

    public boolean isMoveFrame() {
        return frame == slowspeed - 1;
    }

    public void speedUp() {
        if(slowspeed > 1){
            slowspeed--;
            frame = slowspeed - 1;
        }
    }

    public void speedDown() {
        slowspeed++;
    }


}
