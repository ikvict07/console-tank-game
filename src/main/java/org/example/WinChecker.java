package org.example;

public class WinChecker {
    public boolean playerWon(char[][] grid) {
        return isCharInGrid(grid, Symbols.ENEMY_TANK.symbol);
    }

    public boolean enemyWon(char[][] grid) {
        return isCharInGrid(grid, Symbols.TANK.symbol);
    }

    private boolean isCharInGrid(char[][] grid, char symbol) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == symbol) {
                    return false;
                }
            }
        }
        return true;
    }
}
