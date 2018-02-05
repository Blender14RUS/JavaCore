package firstTask;

import firstTask.utils.Point;
import firstTask.utils.Rectangle;

public class IntersectionArea {
    static public void main(String[] args) {
        //calc of the intersection area rectangles in the independent of the sign
        Rectangle rectangle_1 = new Rectangle(new Point(-5, 5), new Point(0, 0));
        Rectangle rectangle_2 = new Rectangle(new Point(-5, -5), new Point(1,1));
        System.out.println("IntersectionArea: " + Area(rectangle_1, rectangle_2));

    }


    static double Area(Rectangle first, Rectangle second){
        double x = 0, x1 = 0;
        double y = 0, y1 = 0;
        if ((second.getA().getX() >= first.getA().getX() && second.getA().getX() <= first.getD().getX())
                || (first.getA().getX() >= second.getA().getX() && first.getA().getX() <= second.getD().getX())){
            x = Math.max(first.getA().getX(), second.getA().getX());
        }
        if ((second.getD().getX() >= first.getA().getX() && second.getD().getX() <= first.getD().getX())
                || (first.getD().getX() >= second.getA().getX() && first.getD().getX() <= second.getD().getX())){
            x1 = Math.min(first.getD().getX(), second.getD().getX());
        }

        if ((second.getA().getY() >= first.getA().getY() && second.getA().getY() <= first.getB().getY())
                || (first.getA().getY() >= second.getA().getY() && first.getA().getY() <= second.getB().getY())){
            y = Math.max(first.getA().getY(), second.getA().getY());
        }
        if ((second.getB().getY() >= first.getA().getY() && second.getB().getY() <= first.getB().getY())
                || (first.getB().getY() >= second.getA().getY() && first.getB().getY() <= second.getB().getY())){
            y1 = Math.min(first.getB().getY(), second.getB().getY());
        }

        return ( Math.abs(x-x1)*Math.abs(y-y1) );
    }
}
