package com.mlesniak;

public class Main extends PixelRenderer {
    public static void main(String... args) {
        new Main()
                .size(1200, 800)
                .show();
    }

    private final Blob[] blobs = new Blob[]{
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
            new Blob(random(width), random(height), random(10, 40)),
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
                double c = getColorScaleFactor(x, y);

                int r = (int) (c * 0xFF);
                int g = (int) (c * 0x23);
                int b = (int) (c * 0x1F);
                setPixel(x, y, r, g, b);
            }
        }
    }

    private double getColorScaleFactor(int x, int y) {
        double c = 0.0;
        for (Blob blob : blobs) {
            var d = distSquared(x, y, blob.x, blob.y);
            if (d != 0) {
                // Math.sqrt(d) is very expensive, and
                // while it allows for way more precise
                // computations, the visual effects
                // with this more pragmatic approach
                // are nice as well.
                c += blob.radius / (d * 0.0005);
            }
        }
        return c;
    }
}
