package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ballistic {
    private int height;
    private int width;
    public static final double g = 9.81;
    public void drawBallisticCurve(int tank_x, int tank_y, char[][] grid, int angle, int velocity, double time) {
        double distance = calculateDistance(angle, velocity, time);
        double timePerDistance = time / distance;

        int end_y = 0;
        int end_x = 0;
        for (int i = 0; i < distance; i++) { // Iterating through every x-cell in grid
            double current_time = i * timePerDistance;

            int x = (int) (tank_x + velocity * Math.cos(Math.toRadians(angle)) * current_time);
            int y = (int) (tank_y - (velocity * Math.sin(Math.toRadians(angle)) * current_time - 0.5 * g * current_time * current_time));

            if ((y - 1 < height) && (x - 1 < width) && 0 < y && 0 < x) { // Checking if curve is in bounds
                grid[y][x - 1] = '*';
            }

            if (hitSomething(tank_x, x, y, grid)) {
                showHitArea(x, y, grid);
                return;
            }
            end_y = y;
            end_x = x;
        }
        boolean isFloorBelow = (grid[end_y + 1][(int) distance] == '=');
        if (!isFloorBelow) { // No floor. Need to continue falling
            continueFalling(tank_x, end_x, end_y, grid);
        }
    }

    public void drawBallisticCurveForEnemy(int tank_x, int tank_y, char[][] grid, int angle, int velocity, double time) {
        double distance = calculateDistance(angle, velocity, time);
        double timePerDistance = time / distance;

        int end_y = 0;
        int end_x = 0;
        for (int i = tank_x; i > tank_x - distance; i--) {
            double current_time = Math.abs(i - tank_x) * timePerDistance;
            int x = i;
            int y = (int) (tank_y - (velocity * Math.sin(Math.toRadians(angle)) * current_time - 0.5 * g * current_time * current_time));

            if ((y - 1 < height) && (x >= 0) && (x < width) && (y > 0)) {
                grid[y][x] = '*';
            }

            if (hitSomething(tank_x, x, y, grid)) {
                showHitArea(x, y, grid);
                break;
            }
            end_x = x;
            end_y = y;
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

    private boolean hitSomething(int tank_x, int x, int y, char[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                if (Math.abs(tank_x - x) < 3) continue;

                if (i == y - 1 || i == y || i == y + 1) {
                    if (j == x - 1 || j == x || j == x + 1) {
                        char signAtCoordinates = grid[i][j];
                        if (signAtCoordinates == '=' || signAtCoordinates == 'T') {
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
