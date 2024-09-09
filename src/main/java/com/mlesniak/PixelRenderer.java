package com.mlesniak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

abstract class PixelRenderer {
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;

    private JFrame window;
    private Canvas canvas;
    BufferedImage image;

    protected int width = 400;
    protected int height = 400;
    protected int tick;
    protected char key;

    private void setup() {
        window = new JFrame("Window");
        centerWindow(width, height);
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                key = e.getKeyChar();
            }
        });

        this.canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(image, 0, 0, width, height, null);
            }

            @Override
            public void update(Graphics g) {
                paint(g);
            }
        };
        this.canvas.setSize(width, height);
        window.add(canvas);
        window.pack();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public PixelRenderer size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    private void centerWindow(int width, int height) {
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var x = screenSize.width / 2 - width / 2;
        var y = screenSize.height / 2 - height / 2;
        window.setLocation(x, y);
    }

    public void show() {
        setup();
        window.setVisible(true);
        new Thread(() -> {
            while (true) {
                long startTime = System.currentTimeMillis();
                update();
                canvas.repaint();
                long elapsedTime = System.currentTimeMillis() - startTime;
                long sleepTime = FRAME_TIME - elapsedTime;
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                } else if (tick > 10) {
                    System.err.println("Unable to manage FPS " + FPS + ", sleep time over " + (-1 * sleepTime));
                }
                tick++;
            }
        }).start();
    }

    protected void exit() {
        System.exit(0);
    }

    protected void setPixel(int x, int y, int r, int g, int b) {
        int c = (r << 16) + (g << 8) + b;
        image.setRGB(x, y, c);
    }

    protected void setPixel(int x, int y, double gray) {
        int c = clamp((int) (gray * 255), 0, 255);
        setPixel(x, y, c, c, c);
    }

    static int clamp(int v, int min, int max, int min2, int max2) {
        float r = v / ((float) max - min);
        return (int) (min2 + r * ((float) max2 - min2));
    }

    static double clamp(double v, double min, double max, double min2, double max2) {
        double r = v / (max - min);
        return min2 + r * (max2 - min2);
    }

    static int clamp(int v, int min, int max) {
        if (v < min) {
            return min;
        }
        if (v > max) {
            return max;
        }
        return v;
    }

    static double rad2deg(double radians) {
        return radians * (180.0 / Math.PI);
    }

    public static double deg2rad(double degrees) {
        return degrees * (Math.PI / 180.0);
    }

    public static double random(double max) {
        return Math.random() * max;
    }

    public static double random(double min, double max) {
        return Math.random() * (max-min) + min;
    }

    abstract void update();
}
