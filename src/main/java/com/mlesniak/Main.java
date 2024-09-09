package com.mlesniak;

public class Main extends PixelRenderer {
    public static void main(String... args) {
        new Main()
                .size(800, 800)
                .show();
    }

    private Blob[] blobs = new Blob[]{
            new Blob(random(width), random(height), random(10, 50)),
            new Blob(random(width), random(height), random(10, 50)),
            new Blob(random(width), random(height), random(10, 50)),
            new Blob(random(width), random(height), random(10, 50)),
            new Blob(random(width), random(height), random(10, 50)),
    };

    @Override
    void update() {
        if (key == 27) {
            exit();
        }

        for (Blob blob : blobs) {
            blob.update(this);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double c = 0.0;
                for (Blob blob : blobs) {
                    var d = dist(x, y, blob.x, blob.y);
                    if (d == 0) {
                        c = 0;
                    } else {
                        c += blob.radius / d;
                    }
                }
                setPixel(x, y, c);
            }
        }
    }

    static double dist(double x, double y, double a, double b) {
        return Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
    }
}
