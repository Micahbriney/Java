public class Util {

    public static double perimeter(Circle circle){
        return 2.0 * Math.PI * circle.getRadius();
    }

    public static double perimeter(Rectangle rectangle){
        double length = Math.abs(rectangle.getBottomRight().getX() - rectangle.getTopLeft().getX());
        double width = Math.abs(rectangle.getBottomRight().getY() - rectangle.getTopLeft().getY());
        return 2.0 * length + 2.0 * width;
    }

    public static double perimeter(Polygon polygon){
        double perimeter = 0;
        for (int i = 0; i < polygon.getPoints().size(); i++) {
            if (i + 1 == polygon.getPoints().size()) {
                double deltaY = polygon.getPoints().get(0).getY() - polygon.getPoints().get(i).getY();
                double deltaX = polygon.getPoints().get(0).getX() - polygon.getPoints().get(i).getX();
                perimeter += Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
            }
            else{
                double deltaY = polygon.getPoints().get(i + 1).getY() - polygon.getPoints().get(i).getY();
                double deltaX = polygon.getPoints().get(i + 1).getX() - polygon.getPoints().get(i).getX();
                perimeter += Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
            }
        }
        return perimeter;
    }
}