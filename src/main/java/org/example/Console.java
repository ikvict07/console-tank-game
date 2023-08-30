package org.example;

import java.util.Random;
import java.util.Scanner;

import static org.example.ConsoleColors.*;
import static org.example.Symbols.*;

public class Console {
    private static boolean isTankCreated = false;
    private static boolean isEnemyTankCreated = false;


    static int tankPos_X;
    static int tankPos_Y;
    static int enemyTankPos_X;
    static int enemyTankPos_Y;

    static void prepareField(int height, int width, char[][] grid) {
        int midHeight = height / 2;
        int midWidth = width / 2;
        for (int w = 0; w < width; w++) {
            double radians = ((double) w / 50) * 2 * Math.PI;
            int y = midHeight + (int) (Math.sin(radians / 2.5) * midHeight) / 2 + midHeight / 2 + midHeight / 10;
            y = Math.max(Math.min(y, height - 1), 0);
            grid[y][w] = GROUND.symbol;


            if (w == midWidth && !isTankCreated || new Random().nextInt(50) == 1 && !isTankCreated) {
                drawTank(y, w, grid);
            }

            if (w > midWidth && new Random().nextInt(50) == 1 && !isEnemyTankCreated || w == width - 15 && !isEnemyTankCreated) {
                drawEnemyTank(y, w, grid);
            }
        }
    }

    static void printGrid(int height, int width, char[][] grid) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                char symbol = grid[h][w];
                if (symbol == GROUND.symbol) {
                    System.out.print(ANSI_GREEN_BACKGROUND.getCode() + ANSI_GREEN.getCode() + symbol + ANSI_RESET.getCode());
                } else if (symbol == TANK.symbol) {
                    System.out.print(ANSI_PURPLE_BACKGROUND.getCode() + ANSI_PURPLE.getCode() + symbol + ANSI_RESET.getCode());
                } else if (symbol == ENEMY_TANK.symbol) {
                    System.out.print(ANSI_RED_BACKGROUND.getCode() + ANSI_RED.getCode() + symbol + ANSI_RESET.getCode());
                } else if (symbol == BALLISTIC_CURVE.symbol) {
                    System.out.print(ANSI_CYAN.getCode() + symbol + ANSI_RESET.getCode());
                } else if (symbol == HIT_AREA.symbol) {
                    System.out.print(ANSI_BLUE_BACKGROUND.getCode() + ANSI_BLACK.getCode() + symbol + ANSI_RESET.getCode());
                } else {
                    System.out.print(EMPTY_CHAR.symbol);
                }
            }
            System.out.println();
        }
    }

    static void fillGridWithEmptyChars(int height, int width, char[][] grid) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                grid[h][w] = EMPTY_CHAR.symbol;
            }
        }
    }

    private static void drawTank(int height, int wight, char[][] grid) {
        grid[height - 1][wight] = '%';
        isTankCreated = true;
        tankPos_X = wight;
        tankPos_Y = height;
    }

    private static void drawEnemyTank(int height, int wight, char[][] grid) {
        grid[height - 1][wight] = 'T';
        isEnemyTankCreated = true;
        enemyTankPos_X = wight;
        enemyTankPos_Y = height;
    }

    static int takeAngle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the angle (0-90): ");
        return scanner.nextInt();
    }

    static int takeVelocity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the velocity (0-100) *optimal is 20-50: ");
        return scanner.nextInt();
    }

}