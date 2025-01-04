import java.awt.Point;
import java.awt.Color;

public class Circle implements Shape {

    private double radius;
    private Point center;
    private Color color;

    public Circle(double radius, Point center, Color color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2.0);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getPerimeter() {
        return Math.PI * 2 * radius;
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void translate(Point p) {
        center.setLocation(center.getX() + p.getX(),
                           center.getY() + p.getY());
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) { //Sets the radius of the Circle
        this.radius = radius;
    }

    public Point getCenter() {              //Returns the center of the Circle
        return center;
    }

    public boolean equals(Object other){               //Overrides the equals method inherited from Object in the manner we discussed in class.
        if (other == null)
            return false;

        if(!this.getClass().equals(other.getClass()))
            return false;

        Circle c = (Circle)other;

        return radius == c.radius && center.equals(c.center) && color.equals(c.color);

    }

}
