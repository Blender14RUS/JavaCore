package firstTask;

import firstTask.utils.Point;

public class Area {
    public static void main(String[] args) {
        Point first = new Point(0.5, -2.0);
        Point second = new Point(-1.5, -1.0);
        System.out.println("Area: " + square(first, second));
        
    }

    static double square(Point first, Point second){
        return ( Math.abs(first.getX()-second.getX())*Math.abs(first.getY()-second.getY()) );
    }
}
