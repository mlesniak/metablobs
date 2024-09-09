package com.mlesniak;

import static com.mlesniak.PixelRenderer.random;

public class Blob {
    double x, y, radius;
    double vx = random(-10, 10);
    double vy = random(-10, 10);

    public Blob(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void update(PixelRenderer main) {
        if (x - radius < 0 || x + radius > main.width) {
            vx *= -1;
        }
        if (y - radius < 0 || y + radius > main.height) {
            vy *= -1;
        }

        x += vx;
        y += vy;
    }
}
