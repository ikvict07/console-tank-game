package org.example;

public class Utils {
    static char[][] deepCopy(char[][] grid) {
        char[][] new_grid = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            // Copy each row array
            new_grid[i] = grid[i].clone();
        }
        return new_grid;
    }
}
