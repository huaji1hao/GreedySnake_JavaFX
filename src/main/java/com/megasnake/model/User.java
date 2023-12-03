package com.megasnake.model;

/**
 * Represents a user in the game.
 *
 * @author Junfeng ZHU
 */
public class User implements Comparable<User> {
    String username;
    int score;

    /**
     * Creates a new user with the given username and score.
     *
     * @param username The username of the user.
     * @param score The score of the user.
     */
    public User(String username, int score){
        this.username = username;
        this.score = score;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the score of the user.
     *
     * @return The score of the user.
     */
    public int getScore(){
        return score;
    }

    /**
     * Compares this user to another user.
     *
     * @param other The other user to compare to.
     * @return 0 if the users have the same score, a positive integer if this user has a higher score, and a negative integer if this user has a lower score.
     */
    @Override
    public int compareTo(User other) {
        return Integer.compare(other.score, this.score); // Sort in descending order of score
    }

    /**
     * Checks if this user is equal to another user.
     *
     * @param obj The other user to compare to.
     * @return True if the users have the same score, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        return score == user.score;
    }

    /**
     * Returns the hash code of this user.
     *
     * @return The hash code of this user.
     */
    @Override
    public int hashCode() {
        return score;
    }

}
