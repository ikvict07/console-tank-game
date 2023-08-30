package org.example;


public class Game {
    public void start(int height, int width, int amplitude) {

        char[][] grid = new char[height][width];

        Console.fillGridWithEmptyChars(height, width, grid);

        Console.prepareField(height, width, amplitude, grid);

        Ballistic ballistic = new Ballistic(height, width);
        MyTank tank = new MyTank(Console.tankPos_X, Console.tankPos_Y, height, width, ballistic);
        EnemyTank enemyTank = new EnemyTank(Console.enemyTankPos_X, Console.enemyTankPos_Y, height, width, ballistic);
        WinChecker checker = new WinChecker();

        char[][] old_grid = Utils.deepCopy(grid);


        Console.printGrid(height, width, grid);

        while (true) {
            try {
                tank.setCoordinates(Console.takeAngle(), Console.takeVelocity());
                tank.shoot(grid);
                Console.printGrid(height, width, grid);

                if (checker.playerWon(grid)) {
                    System.out.println("YOU WON!!!");
                    return;
                }
                Thread.sleep(2000);

                grid = Utils.deepCopy(old_grid);
                enemyTank.shoot(grid);
                Console.printGrid(height, width, grid);

                if (checker.enemyWon(grid)) {
                    System.out.println("You lost((");
                    return;
                }
                Thread.sleep(2000);

                grid = Utils.deepCopy(old_grid);
                Console.printGrid(height, width, grid);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
