package me.lachy.hypixelutils.util;

public class ScreenArea {

    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    public ScreenArea(int x, int y, int width, int height) {
        this.minX = x;
        this.minY = y;
        this.maxX = x + width;
        this.maxY = y + height;
    }

    public boolean isWithin(int x, int y) {
        if (x < this.minX || x > this.maxX) {
            return false;
        }
        if (y < this.minY || y > this.maxY) {
            return false;
        }
        return true;
    }
}
