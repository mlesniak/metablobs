package com.mlesniak;

import static com.mlesniak.PixelRenderer.random;

public class Blob {
    public double x, y, radius;
    public double vx = random(-10, 10);
    public double vy = random(-10, 10);

    public Blob(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void update(PixelRenderer main) {
        if (x < 0 || x > main.width) {
            vx *= -1;
        }
        if (y < 0 || y > main.height) {
            vy *= -1;
        }

        x += vx;
        y += vy;
    }
}
