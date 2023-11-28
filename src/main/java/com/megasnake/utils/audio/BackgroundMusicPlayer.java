package com.megasnake.utils.audio;

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
        stopMusic();
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
            mediaPlayer.pause();
        }
    }

    public static void unmuteMusic() {
        isMuted = false;
        if (mediaPlayer != null) {
            mediaPlayer.setMute(false);
            mediaPlayer.play();
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
