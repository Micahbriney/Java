public class Bigger {

    static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon){
        double cirPerim = Util.perimeter(circle);
        double recPerim = Util.perimeter(rectangle);
        double polPerim = Util.perimeter(polygon);

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
