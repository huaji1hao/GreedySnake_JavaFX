package com.megasnake.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;

    public MusicPlayer(String filename) {
        URL resource = getClass().getResource(filename);
        if (resource == null) {
            System.err.println("Music file not found: " + filename);
            return;
        }
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        if (mediaPlayer != null && !BackgroundMusicPlayer.isMuted()) {
            mediaPlayer.play();
        }
    }

    public static void playMusic(String filename) {
        if (filename == null || BackgroundMusicPlayer.isMuted()) return;
        MusicPlayer musicPlayer = new MusicPlayer(filename);
        musicPlayer.play();
    }
}
