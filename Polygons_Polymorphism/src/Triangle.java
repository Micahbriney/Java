import java.awt.Point;
import java.awt.Color;

public class Triangle implements Shape {

    private Point a;
    private Point b;
    private Point c;
    private Color color;


    public Triangle(Point a, Point b, Point c, Color color){
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = color;
    }


    @Override
    public double getArea() {
        return Math.abs(
                (
                    a.getX() * (b.getY() - c.getY()) +
                    b.getX() * (c.getY() - a.getY()) +
                    c.getX() * (a.getY() - b.getY()))
                    / 2);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getPerimeter() {
        double ab = getDistance(a, b);
        double bc = getDistance(b, c);
        double ca = getDistance(c, a);
        return ab + bc + ca;
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void translate(Point p) {
        a.setLocation(a.getX() + p.getX(),
                      a.getY() + p.getY());
        b.setLocation(b.getX() + p.getX(),
                      b.getY() + p.getY());
        c.setLocation(c.getX() + p.getX(),
                      c.getY() + p.getY());

    }

    public Point getVertexA() {          //Returns the Point representing vertex A of the Triangle.
        return a;
    }

    public Point getVertexB() {          //Returns the Point representing vertex B of the Triangle.
        return b;
    }

    public Point getVertexC() {          //Returns the Point representing vertex C of the Triangle.
        return c;
    }

    public boolean equals(Object other) {             //Overrides the equals method inherited from Object in the manner we discussed in class.
        if (other == null)
            return false;

        if (!this.getClass().equals(other.getClass()))
            return false;

        Triangle t = (Triangle)other;

        return a.equals(t.a) && b.equals(t.b) && c.equals(t.c) && color.equals(t.color);
    }

    private double getDistance(Point p1, Point p2){
        return Math.sqrt(
                Math.pow(p2.getX() - p1.getX(), 2.0) +
                        Math.pow(p2.getY() - p1.getY(), 2.0));
    }
}
