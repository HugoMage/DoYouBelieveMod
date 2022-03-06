package com.hugomage.doyoubelieve.common.utils;

import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.Nullable;

public class Area {
    private int x;
    private int y;
    private int width;
    private int height;

    public Area(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPosOver(Vector2f vector2f) {
        return isPosOver((int) vector2f.x, (int) vector2f.y);
    }

    public boolean isPosOver(int x, int y) {
        return x > getX() && x <= getX() + getWidth() && y > getY() && y <= getY() + getHeight();
    }

    public boolean isOverlapping(Area area) {
        return isPosOver(area.getX(), area.getY());
    }

    @Nullable
    public Area getOverlappingArea(Area area) {
        if (!isOverlapping(area)) return null;
        boolean thisOnLeft = this.getX() < area.getX();
        boolean thisOneTop = this.getY() < area.getY();

        int overlappingWidth = thisOnLeft ? (getX() + getWidth()) - area.getX() : (area.getX() + area.getWidth()) - getX();
        int overlappingHeight = thisOneTop ? (getY() + getHeight()) - area.getY() : (area.getY() + area.getHeight()) - getY();

        return new Area(
                thisOnLeft ? (getX() + getWidth()) - overlappingWidth : (area.getX() + area.getWidth()) - overlappingWidth,
                thisOneTop ? (getY() + getHeight()) - overlappingHeight : (area.getY() + area.getHeight()) - overlappingHeight,
                overlappingWidth,
                overlappingHeight
        );
    }
}
