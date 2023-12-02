package com.megasnake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testCreateUser() {
        User user = new User("testUser", 100);
        assertNotNull(user, "User should be created");
    }

    @Test
    void testGetUsername() {
        User user = new User("testUser", 100);
        assertEquals("testUser", user.getUsername(), "Username should match the one provided in constructor");
    }

    @Test
    void testGetScore() {
        User user = new User("testUser", 100);
        assertEquals(100, user.getScore(), "Score should match the one provided in constructor");
    }

    @Test
    void testCompareTo() {
        User user1 = new User("user1", 100);
        User user2 = new User("user2", 200);
        assertTrue(user1.compareTo(user2) > 0, "user1 should have a higher score than user2");
    }

    @Test
    void testEquals() {
        User user1 = new User("user1", 100);
        User user2 = new User("user2", 100);
        User user3 = new User("user3", 200);

        assertEquals(user1, user2, "user1 should be equal to user2");
        assertNotEquals(user1, user3, "user1 should not be equal to user3");
    }

    @Test
    void testHashCode() {
        User user = new User("testUser", 100);
        assertEquals(100, user.hashCode(), "Hashcode should be equal to the user's score");
    }
}
