package com.megasnake.utils.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for playing music and sound effects.
 */
public class MusicPlayer implements Runnable {
    private final String filename;
    private static final Map<String, Media> mediaCache = new HashMap<>();

    private static final String[] MUSIC_FILES = new String[]{"/audio/eat.mp3", "/audio/hit.mp3", "/audio/gem.mp3", "/audio/coin.mp3", "/audio/game-over.mp3", "/audio/laugh.mp3"};

    /**
     * Creates a new MusicPlayer object.
     *
     * @param filename The name of the music file to play.
     */
    public MusicPlayer(String filename) {
        this.filename = filename;
    }

    /**
     * Plays the music file.
     */
    @Override
    public void run() {
        URL resource = getClass().getResource(filename);
        if (resource == null) {
            System.err.println("Music file not found: " + filename);
            return;
        }
        Media media = new Media(resource.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // when the mute button is pressed, the music will stop playing
        if (!BackgroundMusicPlayer.isMuted()) {
            mediaPlayer.play();
        }
    }

    /**
     * Preloads all music files.
     */
    public static void preloadMedia(){
        preloadMedia(MUSIC_FILES);
    }

    /**
     * Preloads the specified music files.
     *
     * @param filenames The names of the music files to preload.
     */
    public static void preloadMedia(String[] filenames) {
        for (String filename : filenames) {
            URL resource = MusicPlayer.class.getResource(filename);
            if (resource != null) {
                Media media = new Media(resource.toString());
                mediaCache.put(filename, media);
            } else {
                System.err.println("Music file not found: " + filename);
            }
        }
    }

    /**
     * Plays the specified music file.
     *
     * @param filename The name of the music file to play.
     */
    public static void playMusic(String filename) {
        if (filename == null || BackgroundMusicPlayer.isMuted()) return;
        Media media = mediaCache.get(filename);
        if (media != null) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } else {
            System.err.println("Media not preloaded: " + filename);
        }
    }

}
