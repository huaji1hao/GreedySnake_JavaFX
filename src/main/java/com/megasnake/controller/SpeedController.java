package com.megasnake.controller;

public class SpeedController {
    private int slowspeed; // The larger the slowspeed, the slower the snake moves
    private int frame;

    public SpeedController(int slowspeed) {
        this.slowspeed = slowspeed;
        this.frame = slowspeed - 1;
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
        if(slowspeed > 1) slowspeed--;
    }

    public void speedDown() {
        slowspeed++;
    }


}
