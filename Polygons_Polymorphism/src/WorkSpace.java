import java.awt.Point;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class WorkSpace {

    private List<Shape> shapes = new ArrayList<Shape>();


    public void add(Shape shape) {                      //Adds Shape object to end of List
        shapes.add(shape);
    }

    public Shape get(int index) {                       //Returns the specified Shape from the WorkSpace
        return shapes.get(index);
    }

    public List<Circle> getCircles(){                   //Returns List of all the Circle objects
        List<Circle> circles = new ArrayList<Circle>();

        for (Shape shape : shapes) {
            if (shape.getClass().equals(Circle.class)){
                circles.add((Circle) shape);
            }
        }
        return circles;
    }

    public List<Rectangle> getRectangles() {            //Returns List of all the Rectangle objects
        List<Rectangle> rectangles = new ArrayList<Rectangle>();

        for (Shape shape : shapes) {
            if (shape.getClass().equals(Rectangle.class)) {
                rectangles.add((Rectangle) shape);
            }
        }

        return rectangles;
    }

    public List<Triangle> getTriangles() {              //Returns List of all the Triangle objects
        List<Triangle> triangles = new ArrayList<Triangle>();

        for (Shape shape : shapes) {
            if (shape.getClass().equals(Triangle.class)){
                triangles.add((Triangle)shape);
            }
        }
        return triangles;
    }


    public List<Shape> getShapesByColor(Color color) {  //Returns List of Shaped that match the specified Color.
        List<Shape> shapeColors = new ArrayList<Shape>();

        for (Shape shape : shapes) {
            if (shape.getColor().equals(color)){
                shapeColors.add(shape);
            }
        }
        return shapeColors;
    }

    public double getAreaOfAllShapes() {                //Returns sum of all shape areas in Workspace
        double totArea = 0;
        for (Shape shape : shapes) {
            totArea += shape.getArea();
        }
        return totArea;
    }

    public double getPerimeterOfAllShapes() {           //Returns sum of all shape perimeters in WorkSpace
        double totPerim = 0;
        for (Shape shape: shapes) {
            totPerim += shape.getPerimeter();
        }
        return totPerim;
    }

    public int size() {                                 //Returns the number of Shapes contained by the WorkSpace
        return shapes.size();
    }


}
