import java.awt.Color;
import java.awt.Point;

public interface Shape {

    double getArea();           // - Returns the area of the Shape
    Color getColor();           // - Returns the Color of the Shape.
    double getPerimeter();      // - Returns the perimeter of the Shape
    void setColor(Color c);     // - Sets the Color of the Shape.
    void translate(Point p);    // - Translates the entire shape by the (x,y) coordinates of a Point

}
