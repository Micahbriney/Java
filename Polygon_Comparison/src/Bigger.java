public class Bigger {

    static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double cirPerim = circle.perimeter();
        double recPerim = rectangle.perimeter();
        double polPerim = polygon.perimeter();

        if(cirPerim > recPerim && cirPerim > polPerim)
        {
            return cirPerim;
        }
        else if(recPerim > polPerim)
        {
            return recPerim;
        }
        else
        {
            return polPerim;
        }

    }

}
