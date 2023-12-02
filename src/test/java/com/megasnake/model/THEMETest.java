package com.megasnake.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class THEMETest {

    @Test
    void testEnumValues() {
        THEME[] themes = THEME.values();
        assertEquals(4, themes.length, "There should be 4 themes");
    }

    @Test
    void testGrassTheme() {
        THEME theme = THEME.GRASS;
        assertEquals("/background/grass_background_small.png", theme.getUrl(), "URL should match for GRASS theme");
        assertEquals(0, theme.getLevel(), "Level should be 0 for GRASS theme");
        assertEquals("Easy", theme.getLevelText(), "Level text should be 'Easy' for GRASS theme");
    }

    @Test
    void testCandyTheme() {
        THEME theme = THEME.CANDY;
        assertEquals("/background/candy_background_small.png", theme.getUrl(), "URL should match for CANDY theme");
        assertEquals(1, theme.getLevel(), "Level should be 1 for CANDY theme");
        assertEquals("Medium", theme.getLevelText(), "Level text should be 'Medium' for CANDY theme");
    }

    @Test
    void testLavaTheme() {
        THEME theme = THEME.LAVA;
        assertEquals("/background/lava_background_small.jpg", theme.getUrl(), "URL should match for LAVA theme");
        assertEquals(2, theme.getLevel(), "Level should be 2 for LAVA theme");
        assertEquals("Hard", theme.getLevelText(), "Level text should be 'Hard' for LAVA theme");
    }

    @Test
    void testSpaceTheme() {
        THEME theme = THEME.SPACE;
        assertEquals("/background/space_background_small.jpg", theme.getUrl(), "URL should match for SPACE theme");
        assertEquals(3, theme.getLevel(), "Level should be 3 for SPACE theme");
        assertEquals("Hell", theme.getLevelText(), "Level text should be 'Hell' for SPACE theme");
    }
}
