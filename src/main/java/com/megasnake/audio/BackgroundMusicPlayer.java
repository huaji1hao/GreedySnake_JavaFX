package com.megasnake.audio;

/**
 * A class for playing background music in a game. It extends Thread to allow music playing in the background.
 */
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class BackgroundMusicPlayer {
    private static MediaPlayer mediaPlayer;
    private static boolean isMuted; // Flag for muting the music

    private BackgroundMusicPlayer() {
        // Prevent instantiation
    }

    private static void initializeMusicPlayer(String filename) {
        URL resource = BackgroundMusicPlayer.class.getResource(filename);
        if (resource == null) {
            System.err.println("Music file not found: " + filename);
            return;
        }
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static void playMusic() {
        if (mediaPlayer != null && !isMuted) {
            mediaPlayer.play();
        }
    }

    public static void repeatMusic(String filename) {
        initializeMusicPlayer(filename);
        playMusic();
    }

    public static void stopMusic() {
        // end the music
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void muteMusic() {
        isMuted = true;
        if (mediaPlayer != null) {
            mediaPlayer.setMute(true);
        }
    }

    public static void unmuteMusic() {
        isMuted = false;
        if (mediaPlayer != null) {
            mediaPlayer.setMute(false);
        }
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void operateMute() {
        if (isMuted) {
            unmuteMusic();
        } else {
            muteMusic();
        }
    }
}
