package firstTask;

public class Area {
    public static void main(String[] args) {
        Coordinate first = new Coordinate(0.5, -2.0);
        Coordinate second = new Coordinate(-1.5, -1.0);
        System.out.println("Area: " + square(first, second));
    }

    static double square(Coordinate first, Coordinate second){
        return ( Math.abs(first.getX()-second.getX())*Math.abs(first.getY()-second.getY()) );
    }
}
