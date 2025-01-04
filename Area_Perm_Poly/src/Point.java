
public class Point {

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

     public double getRadius(){
        return Math.sqrt(Math.pow(x, 2.0)+Math.pow(y, 2.0));
     }

     public double getAngle(){
        return Math.atan2(y, x);
     }

     public Point rotate90(){
        double temp = x;
        Point rotate = new Point(x ,y);

        rotate.x = - y;
        rotate.y = temp;
        return rotate;
     }

}

