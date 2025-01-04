import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon(List<Point> points){
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public double perimeter(){
        double perimeter = 0;
        for (int i = 0; i < getPoints().size(); i++) {
            if (i + 1 == getPoints().size()) {
                double deltaY = getPoints().get(0).getY() - getPoints().get(i).getY();
                double deltaX = getPoints().get(0).getX() - getPoints().get(i).getX();
                perimeter += Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
            }
            else{
                double deltaY = getPoints().get(i + 1).getY() - getPoints().get(i).getY();
                double deltaX = getPoints().get(i + 1).getX() - getPoints().get(i).getX();
                perimeter += Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
            }
        }
        return perimeter;
    }
}