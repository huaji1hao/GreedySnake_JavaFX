package com.megasnake.controller;

public class SpeedController {
    private int slowspeed; // The larger the slowspeed, the slower the snake moves
    private int frame;

    public SpeedController() {
        this.slowspeed = 8;
        this.frame = slowspeed - 1;
    }

    public int getSpeedLevel() {
        return 10 - slowspeed;
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

    public void speedUp(int n) {
        for(int i = 0; i < n; i++) speedUp();
    }

    public void speedDown() {
        if(slowspeed < 10) slowspeed++;
    }

    public void speedDown(int n) {
        for(int i = 0; i < n; i++) slowspeed++;
    }


}
