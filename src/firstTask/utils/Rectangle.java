package firstTask.utils;

public class Rectangle {
    //hourly, starting with lower left
    private Coordinate a;
    private Coordinate b;
    private Coordinate c;
    private Coordinate d;

    public Rectangle() {
        this.a = new Coordinate(0, 0);
        this.b = new Coordinate(0, 0);
        this.c = new Coordinate(0, 0);
        this.d = new Coordinate(0, 0);
    }

    public Rectangle(Coordinate first, Coordinate second) {
        if ((first.getX() < second.getX()) && (first.getY() < second.getY())){
            this.a = first;
            this.c = second;
            this.b = new Coordinate(first.getX(), second.getY());
            this.d = new Coordinate(second.getX(), first.getY());
        }
        else if((second.getX() < first.getX()) && (second.getY() < first.getY())){
            this.a = second;
            this.c = first;
            this.d = new Coordinate(first.getX(), second.getY());
            this.b = new Coordinate(second.getX(), first.getY());
        }
        else{
            if (first.getY() > second.getY()) {
                this.b = first;
                this.d = second;
                this.c = new Coordinate(second.getX(), first.getY());
                this.a = new Coordinate(first.getX(), second.getY());
            }
            else{
                this.b = second;
                this.d = first;
                this.a = new Coordinate(second.getX(), first.getY());
                this.c = new Coordinate(first.getX(), second.getY());
            }
        }
    }

    public Coordinate getA() {
        return a;
    }

    public Coordinate getB() {
        return b;
    }

    public Coordinate getC() {
        return c;
    }

    public Coordinate getD() {
        return d;
    }

    public void setCoordinate(Coordinate first, Coordinate second) {
        if (first.getX() < second.getX()){
            this.a = first;
            this.c = second;
            this.b = new Coordinate(first.getX(), second.getY());
            this.d = new Coordinate(second.getX(), first.getY());
        }
        else {
            this.b = first;
            this.d = second;
            this.c = new Coordinate(second.getX(), first.getY());
            this.a = new Coordinate(first.getX(), second.getY());
        }
    }
}
