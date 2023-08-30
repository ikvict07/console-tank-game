package org.example;

import lombok.Data;

import java.util.Random;

@Data
public class EnemyTank implements Tank {
    private int x;
    private int y;
    private int height;
    private int width;
    private Ballistic ballistic;

    private int angle;
    private int velocity;

    public EnemyTank(int x, int y, int height, int width, Ballistic ballistic) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.ballistic = ballistic;
    }


    public void shoot(char[][] grid) {
        setCoordinates();
        double time = (2 * velocity * Math.sin(Math.toRadians(angle)) / Ballistic.g);

        ballistic.drawBallisticCurve(x, y, grid, angle, velocity, time, true);
    }

    private void setCoordinates() {
        Random random = new Random();
        angle = random.nextInt(30, 85);
        velocity = random.nextInt(15, 45);
    }


}

