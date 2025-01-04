import java.io.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Streams {


    public static void readInPoints(List<Point> points)
    {
        try
        {
            File inFile = new File("positions.txt");
            Scanner sc = new Scanner(inFile);
            String line = sc.nextLine();
            double x, y, z;
            while (sc.hasNext())
            {
                String[] words= line.split(",");
                x = Double.parseDouble(words[0]);
                y = Double.parseDouble(words[1]);
                z = Double.parseDouble(words[2]);
                points.add(new Point(x, y , z));
                line = sc.nextLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("Can't open input file.");
        }

    }
    public static void writePoints(List<Point> points)
    {
        try
        {
            PrintStream ps = new PrintStream("drawMe.txt");

            for (Point s : points)
            {
                ps.print(s.getX());
                ps.print(", ");
                ps.print(s.getY());
                ps.print(", ");
                ps.println(s.getZ());
            }
        }
        catch (Exception e)
        {
            System.out.println("Can't open output file.");
        }
    }

    public static void main(String[] args) {
        List<Point> thePoints = new ArrayList<Point>();

        Point test = new Point(1, 2, 3);
        System.out.print(test.getX());
        readInPoints(thePoints);

        // Remove all points that have a z value > 2.0.
        thePoints = thePoints.stream()
                        .filter(s -> s.getZ() <= 2.0)
                        .collect(Collectors.toList());

        // Scale down all the points by 0.5
        thePoints = thePoints.stream()
                        .map(s -> new Point (s.getX() / 2.0,
                                s.getY() / 2.0,
                                s.getZ() / 2.0))
                        .collect(Collectors.toList());


        // Translate all the points by {-150, -37}
        thePoints = thePoints.stream()
                .map(s -> new Point (s.getX() - 150,
                        s.getY() -37,
                        s.getZ()))
                .collect(Collectors.toList());


        writePoints(thePoints);
    }

}
