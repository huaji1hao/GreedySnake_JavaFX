package com.megasnake.audio;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * A class for playing background music in a game. It extends Thread to allow music playing in the background.
 */
public class BackgroundMusicPlayer extends Thread {
    private final String filename; // The path to the music file
    private static Player player; // JLayer player for playing music
    private static boolean isPlaying; // Flag to control music playing
    private static boolean isMuted; // Flag for muting the music
    static PauseTransition pause; // Pause transition for delaying music play

    /**
     * Constructor for BackgroundMusicPlayer.
     * @param filename The path to the music file.
     */
    public BackgroundMusicPlayer(String filename) {
        this.filename = filename;
        this.isPlaying = true;
    }

    /**
     * The run method for the thread. It plays the music in a loop if not muted.
     */
    @Override
    public void run() {
        while (isPlaying && !isMuted) {
            try (InputStream is = getClass().getResourceAsStream(filename);
                 BufferedInputStream bis = new BufferedInputStream(is)) {
                player = new Player(bis);
                player.play();
                if (player.isComplete() && isPlaying) {
                    player.close();
                }
            } catch (Exception e) {
                System.err.println("Error playing the audio file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Static method to start playing music in a loop.
     * @param filename The path to the music file.
     */
    public static void repeatMusic(String filename) {
        if (player != null) stopMusic();

        pause = new PauseTransition(Duration.millis(1));

        pause.setOnFinished(event -> {
            isPlaying = true;
            new BackgroundMusicPlayer(filename).start();
        });
        pause.play();
    }

    /**
     * Static method to stop playing music.
     */
    public static void stopMusic() {
        if (pause != null) pause.stop();
        isPlaying = false;
        if (player != null) player.close();
    }

    /**
     * Static method to mute the music.
     */
    public static void muteMusic() {
        isMuted = true;
        stopMusic(); // Stop current playback when muting
    }

    /**
     * Static method to unmute the music.
     */
    public static void unmuteMusic() {
        isMuted = false;
    }
}
