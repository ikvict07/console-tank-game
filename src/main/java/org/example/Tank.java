package org.example;

import lombok.Data;

@Data
public class Tank {
    private static final double g = 9.8d;
    private int x;
    private int y;
    private int height;
    private int width;
    private Ballistic ballistic;

    public Tank(int x, int y, int height, int width, Ballistic ballistic) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.ballistic = ballistic;
    }

    public void shoot(char[][] grid, int angle, int velocity) {
        System.out.println("Tank coordinates are " + x + " " + y);
        double time = (2 * velocity * Math.sin(Math.toRadians(angle)) / g);

        ballistic.drawBallisticCurve(x, y, grid, angle, velocity, time);
    }
}

