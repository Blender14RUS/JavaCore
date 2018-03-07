package com.javacore;

import com.javacore.utils.Point;
import com.javacore.utils.VerticesOfRectangle;

public class Rectangle {
    public double intersectionArea(VerticesOfRectangle first, VerticesOfRectangle second) {
        double x = 0, x1 = 0;
        double y = 0, y1 = 0;
        if ((second.getA().getX() >= first.getA().getX() && second.getA().getX() <= first.getD().getX())
                || (first.getA().getX() >= second.getA().getX() && first.getA().getX() <= second.getD().getX())) {
            x = Math.max(first.getA().getX(), second.getA().getX());
        }
        if ((second.getD().getX() >= first.getA().getX() && second.getD().getX() <= first.getD().getX())
                || (first.getD().getX() >= second.getA().getX() && first.getD().getX() <= second.getD().getX())) {
            x1 = Math.min(first.getD().getX(), second.getD().getX());
        }

        if ((second.getA().getY() >= first.getA().getY() && second.getA().getY() <= first.getB().getY())
                || (first.getA().getY() >= second.getA().getY() && first.getA().getY() <= second.getB().getY())) {
            y = Math.max(first.getA().getY(), second.getA().getY());
        }
        if ((second.getB().getY() >= first.getA().getY() && second.getB().getY() <= first.getB().getY())
                || (first.getB().getY() >= second.getA().getY() && first.getB().getY() <= second.getB().getY())) {
            y1 = Math.min(first.getB().getY(), second.getB().getY());
        }

        return (Math.abs(x - x1) * Math.abs(y - y1));
    }

    public double area(Point first, Point second) {
        return (Math.abs(first.getX() - second.getX()) * Math.abs(first.getY() - second.getY()));
    }
}
