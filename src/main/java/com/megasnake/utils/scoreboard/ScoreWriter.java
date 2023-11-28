package com.megasnake.utils.scoreboard;

import com.megasnake.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ScoreWriter {
    private ScoreWriter() {}
    private static final String SCORE_FILE_PATH = "score.txt";
    public static void writeScoreToFile(User user) {
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(SCORE_FILE_PATH, true)))) {
            out.println("\n" + user.getUsername() + ", " + user.getScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
