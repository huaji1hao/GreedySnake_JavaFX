package com.megasnake.model.game;

public class User implements Comparable<User> {
    String username;
    int score;

    public User(String username, int score){
        this.username = username;
        this.score = score;
    }

    public String getUsername(){
        return username;
    }

    public int getScore(){
        return score;
    }

    @Override
    public int compareTo(User other) {
        return Integer.compare(other.score, this.score); // Sort in descending order of score
    }
}
