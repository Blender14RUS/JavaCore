package com.javacore.utils;

public class VerticesOfRectangle {
    //hourly, starting with lower left
    private Point a;
    private Point b;
    private Point c;
    private Point d;

    public VerticesOfRectangle() {
        this.a = new Point(0, 0);
        this.b = new Point(0, 0);
        this.c = new Point(0, 0);
        this.d = new Point(0, 0);
    }

    public VerticesOfRectangle(Point first, Point second) {
        if ((first.getX() < second.getX()) && (first.getY() < second.getY())) {
            this.a = first;
            this.c = second;
            this.b = new Point(first.getX(), second.getY());
            this.d = new Point(second.getX(), first.getY());
        } else if ((second.getX() < first.getX()) && (second.getY() < first.getY())) {
            this.a = second;
            this.c = first;
            this.d = new Point(first.getX(), second.getY());
            this.b = new Point(second.getX(), first.getY());
        } else {
            if (first.getY() > second.getY()) {
                this.b = first;
                this.d = second;
                this.c = new Point(second.getX(), first.getY());
                this.a = new Point(first.getX(), second.getY());
            } else {
                this.b = second;
                this.d = first;
                this.a = new Point(second.getX(), first.getY());
                this.c = new Point(first.getX(), second.getY());
            }
        }
    }

    public Point getA() {
        return this.a;
    }

    public Point getB() {
        return this.b;
    }

    public Point getC() {
        return this.c;
    }

    public Point getD() {
        return this.d;
    }

}
