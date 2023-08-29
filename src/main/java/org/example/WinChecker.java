package org.example;

public class WinChecker {
    public boolean playerWon(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'T') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean enemyWon(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '%') {
                    return false;
                }
            }
        }
        return true;
    }
}
