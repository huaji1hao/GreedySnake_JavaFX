package example.audio;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * A simple music player that plays audio files in a separate thread.
 */
public class MusicPlayer extends Thread {
    private final String filename;
    private Player player;

    public MusicPlayer(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try (InputStream is = getClass().getResourceAsStream(filename);
             BufferedInputStream bis = new BufferedInputStream(is)) {
            player = new Player(bis);
            player.play();
        } catch (Exception e) {
            System.err.println("Error playing the audio file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play() {
        this.start();
    }

    public static void playMusic(String filename) {
        new MusicPlayer(filename).play();
    }
}
