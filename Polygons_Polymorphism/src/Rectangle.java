import java.awt.Point;
import java.awt.Color;

public class Rectangle implements Shape{

    private Color color;
    private double height;
    private Point topLeft;
    private double width;

    public Rectangle(double width, double height, Point topLeft, Color color){
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
        this.color = color;
    }


    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getPerimeter() {
        return width + width + height + height;
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }

    @Override
    public void translate(Point p) {
        topLeft.setLocation(topLeft.getX() + p.getX(),
                            topLeft.getY() + p.getY());
    }

    public double getWidth(){               //Returns the width of the Rectangle.
        return  width;
    }

    public void setWidth(double width){     //Sets the width of the Rectangle
        this.width = width;
    }

    public double getHeight(){              //Returns the height of the Rectangle.
        return height;
    }

    public void setHeight(double height){   //Sets the height of the Rectangle
        this.height = height;
    }

    public Point getTopLeft(){              //Returns the Point representing the top left corner of the Rectangle
        return topLeft;
    }

    public boolean equals(Object other){                //Overrides the equals method inherited from Object in the manner we discussed in class.
        if (other == null)
            return false;

        if (!this.getClass().equals(other.getClass()))
            return false;

        Rectangle r = (Rectangle)other;

        return topLeft.equals(r.topLeft) && color.equals(r.color) && height == r.height && width == r.width;
    }

}
