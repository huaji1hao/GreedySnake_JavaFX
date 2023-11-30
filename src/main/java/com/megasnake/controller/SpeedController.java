package com.megasnake.controller;

/**
 * Controls the speed of the movable objects in the game.
 */
public class SpeedController {
    // The larger the speedPool, the slower the speed
    private int speedPool;
    // The frame counter in the speed pool
    private int frame;

    // Default speed pool is 8, which means the movable object moves once every 8 frames
    public SpeedController() {
        this.speedPool = 8;
        // The frame counter starts from speedPool - 1
        this.frame = speedPool - 1;
    }

    /**
     * Returns the speed level of the game.
     *
     * @return The speed level of the game.
     */
    public int getSpeedLevel() {
        return 10 - speedPool;
    }

    /**
     * Returns the frame rate of the game, which is the position of the frame counter in the speed pool.
     *
     * @return The frame rate of the game.
     */
    public double getFrameRate() {
        return 1.0 * frame / speedPool;
    }

    /**
     * Updates the frame counter.
     */
    public void updateFrame() {
        // The frame counter is in the range [0, speedPool - 1]
        frame = (frame + 1) % speedPool;
    }

    /**
     * Returns whether the movable object should move in this frame.
     *
     * @return Whether the movable object should move in this frame.
     */
    public boolean isMoveFrame() {
        return frame == speedPool - 1;
    }

    /**
     * Speeds up the game object by decreasing the speed pool.
     */
    public void speedUp() {
        // Ensure the speed pool is not less than 1
        if(speedPool > 1){
            speedPool--;
            // Reset the frame counter
            frame = speedPool - 1;
        }
    }

    /**
     * Speeds up the game object by decreasing the speed pool.
     *
     * @param n The number of times to speed up.
     */
    public void speedUp(int n) {
        for(int i = 0; i < n; i++) speedUp();
    }

    /**
     * Speeds down the game object by increasing the speed pool.
     */
    public void speedDown() {
        if(speedPool < 10) speedPool++;
    }

    /**
     * Speeds down the game object by increasing the speed pool.
     *
     * @param n The number of times to speed down.
     */
    public void speedDown(int n) {
        for(int i = 0; i < n; i++) speedPool++;
    }

}
