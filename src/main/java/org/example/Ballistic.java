package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ballistic {
    private int height;
    private int width;
    public static final double g = 9.81;
    public void drawBallisticCurve(int tank_x, int tank_y, char[][] grid, int angle, int velocity, double time, boolean isEnemy) {
        double distance = calculateDistance(angle, velocity, time);
        double timePerDistance = time / distance;

        int end_x = 0;
        int end_y = 0;

        for (int i = isEnemy ? tank_x : 0; isEnemy ? i > tank_x - distance : i < distance; i += isEnemy ? -1 : 1) {
            double current_time = isEnemy ? Math.abs(i - tank_x) * timePerDistance : i * timePerDistance;
            int x = isEnemy ? i : (int) (tank_x + velocity * Math.cos(Math.toRadians(angle)) * current_time);
            int y = (int) (tank_y - (velocity * Math.sin(Math.toRadians(angle)) * current_time - 0.5 * g * current_time * current_time)) - 3;


            if ((y - 1 < height) && (y > 0) && (x - 1 < width) && (x > 0)) {
                if (current_time >= time/2) { // if missile falling down need extra checking if velocity is high
                    if (fallingDownChecker(x, y, grid, end_x, end_y, isEnemy)){
                        return;
                    }

                }
                grid[y][x] = '*';
                end_x = x;
                end_y = y;
            }

            if (hitSomething(tank_x, x, y, grid)) {
                showHitArea(x, y, grid);
                return;
            }
            if ((x - 1 >= width) || (x <= 0) || (y >= height)) return;
        }
        boolean isFloorBelow = (grid[end_y + 1][(int) distance] == '=');
        if (!isFloorBelow) { // No floor. Need to continue falling
            continueFalling(tank_x, end_x, end_y, grid);
        }
    }

    private double calculateDistance(int angle, int velocity, double time) {
        double distance = velocity * Math.cos(Math.toRadians(angle)) * time;
        distance = Math.max(Math.min(distance, width - 1), 0);
        return distance;
    }

    private void continueFalling(int tank_x, int x, int y, char[][] grid) {
        for (int i = y; i < grid.length; i++) {
            if (i < 0 || i >= height - 1) continue;
            grid[i][x] = '*';
            if (hitSomething(tank_x, x, i, grid)) {
                showHitArea(x, i, grid);
                break;
            }
        }
    }

    private boolean fallingDownChecker(int x, int y, char[][] grid, int previous_x, int previous_y, boolean isEnemy) {
        int direction = isEnemy ? -1 : 1;

        for (int i = previous_y; i < y; i++) {
            int x_mid = previous_x + direction * (Math.abs(x - previous_x) / 2);
            char symbol = grid[i][x_mid];
            if (symbol == Symbols.GROUND.symbol || symbol == Symbols.ENEMY_TANK.symbol){
                showHitArea(x_mid, i, grid);
                return true;
            }
        }
        return false;
    }

    private boolean hitSomething(int tank_x, int x, int y, char[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                if (Math.abs(tank_x - x) < 3) continue;

                if (i == y - 1 || i == y || i == y + 1) {
                    if (j == x - 1 || j == x || j == x + 1) {
                        char signAtCoordinates = grid[i][j];
                        if (signAtCoordinates == Symbols.GROUND.symbol || signAtCoordinates == Symbols.ENEMY_TANK.symbol) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private void showHitArea(int x, int y, char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == y - 1 || i == y || i == y + 1) {
                    if (j == x - 1 || j == x || j == x + 1) {
                        grid[i][j] = 'X';
                    }
                }
            }
        }
        System.out.println("Hit area is " + x + " " + y);
    }


}
