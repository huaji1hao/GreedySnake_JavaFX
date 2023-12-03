package com.megasnake.utils.scoreboard;

import com.megasnake.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes the user's name and score to a file.
 *
 * @Author Junfeng ZHU
 */
public class ScoreWriter {
    private ScoreWriter() {}
    private static final String SCORE_FILE_PATH = "score.txt";

    /**
     * Writes the user's name and score to a file.
     *
     * @param user The user whose name and score will be written to the file.
     */
    public static void writeScoreToFile(User user) {
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(SCORE_FILE_PATH, true)))) {
            out.println("\n" + user.getUsername() + ", " + user.getScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
