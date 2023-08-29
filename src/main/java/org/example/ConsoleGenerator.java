package org.example;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Random;
import java.util.Scanner;

public class ConsoleGenerator {
    private static final char EMPTY_CHAR = ' ';
    private static final char FILLED_CHAR = '=';
    static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";
    static String ANSI_RESET = "\u001B[0m";
    private static boolean isTankCreated = false;
    private static boolean isEnemyTankCreated = false;


    private static int tankPos_X;
    private static int tankPos_Y;
    private static int enemyTankPos_X;
    private static int enemyTankPos_Y;

    private static void drawSinusoid(int height, int width, char[][] grid) {
        int midHeight = height / 2;
        int midWidth = width / 2;
        for (int w = 0; w < width; w++) {
            double radians = ((double) w / 50) * 2 * Math.PI;
            int y = midHeight + (int) (Math.sin(radians / 2.5) * midHeight) / 2 + midHeight / 2 + midHeight / 10;
            y = Math.max(Math.min(y, height - 1), 0);
            grid[y][w] = FILLED_CHAR;


            if (w == midWidth && !isTankCreated || new Random().nextInt(50) == 1 && !isTankCreated) {
                drawTank(y, w, grid);
            }

            if (w > midWidth && new Random().nextInt(50) == 1 && !isEnemyTankCreated || w == width - 15 && !isEnemyTankCreated) {
                drawEnemyTank(y, w, grid);
            }
        }
    }

    private static void printGrid(int height, int width, char[][] grid) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                char symbol = grid[h][w];
                if (symbol == FILLED_CHAR) {
                    System.out.print(ANSI_GREEN_BACKGROUND + ANSI_GREEN + symbol + ANSI_RESET);
                } else if (symbol == '%') {
                    System.out.print(ANSI_PURPLE_BACKGROUND + ANSI_PURPLE + symbol + ANSI_RESET);
                } else if (symbol == 'T') {
                    System.out.print(ANSI_RED_BACKGROUND + ANSI_RED + symbol + ANSI_RESET);
                }else if (symbol == '*') {
                    System.out.print(ANSI_CYAN + symbol + ANSI_RESET);
                }else if (symbol == 'X') {
                    System.out.print(ANSI_BLUE_BACKGROUND + ANSI_BLACK + ANSI_RED + symbol + ANSI_RESET);
                }
                else {
                    System.out.print(EMPTY_CHAR);
                }
            }
            System.out.println();
        }
    }

    private static void fillGridWithEmptyChars(int height, int width, char[][] grid) {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                grid[h][w] = EMPTY_CHAR;
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

    private static int takeAngle() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the angle: ");
        return scanner.nextInt();
    }

    private static int takeVelocity() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the velocity: ");
        return scanner.nextInt();
    }

    private static char[][] deepCopy(char[][] grid) {
        char[][] new_grid = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            // Copy each row array
            new_grid[i] = grid[i].clone();
        }
        return new_grid;
    }

    public void generate(int height, int width) throws IOException {


        char[][] grid = new char[height][width];

        // Fill array with empty chars
        fillGridWithEmptyChars(height, width, grid);

        // Draw sinusoid line
        drawSinusoid(height, width, grid);

        Ballistic ballistic = new Ballistic(height, width);

        Tank tank = new Tank(tankPos_X, tankPos_Y, height, width, ballistic);
        EnemyTank enemyTank = new EnemyTank(enemyTankPos_X, enemyTankPos_Y, height, width, ballistic);
        WinChecker checker = new WinChecker();

        char[][] old_grid = deepCopy(grid);


        printGrid(height, width, grid);

        while (true) {
            try {
                tank.shoot(grid, takeAngle(), takeVelocity());
                printGrid(height, width, grid);
                if (checker.playerWon(grid)) {
                    System.out.println("YOU WON!!!");
                    return;
                }
                Thread.sleep(2000);

                grid = deepCopy(old_grid);


                Thread.sleep(2000);

                enemyTank.shoot(grid);
                printGrid(height, width, grid);
                if (checker.enemyWon(grid)) {
                    System.out.println("You lost((");
                    return;
                }

                Thread.sleep(2000);
                grid = deepCopy(old_grid);
                printGrid(height, width, grid);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}