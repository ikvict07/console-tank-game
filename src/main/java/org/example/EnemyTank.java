package org.example;

import java.util.Random;

public class EnemyTank extends Tank{
    public EnemyTank(int x, int y, int height, int width, Ballistic ballistic) {
        super(x, y, height, width, ballistic);
    }

    public void shoot(char[][] grid) {
        int angle = new Random().nextInt(30, 65);
        int velocity = new Random().nextInt(15, 30);
        double time = (2 * velocity * Math.sin(Math.toRadians(angle)) / Ballistic.g);

        super.getBallistic().drawBallisticCurveForEnemy(super.getX(), super.getY(), grid, angle, velocity, time);
    }
}
