package com.megasnake.utils.scoreboard;

import com.megasnake.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the user's name and score from the score.txt file.
 *
 * @author Junfeng ZHU
 */
public class ScoreReader {
    private ScoreReader() {}
    private static final String SCORE_FILE_PATH = "score.txt";

    /**
     * Reads the user's name and score from the score.txt file.
     *
     * @return A list of users with their name and score.
     */
    public static List<User> readScoresFromFile() {
        ArrayList<User> usersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    usersList.add(new User(username, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the score.txt file");
        }
        return usersList;
    }

}
