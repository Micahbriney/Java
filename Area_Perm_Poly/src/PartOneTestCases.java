import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartOneTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[][] {new Class[0]});

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testUtilImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "perimeter", "perimeter", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[] {Circle.class},
         new Class[] {Polygon.class},
         new Class[] {Rectangle.class});

      verifyImplSpecifics(Util.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }


   @Test
   public void testPerimCir() {
      Point center = new Point(0.0, 0.0);
      double radius = 8.0;
      double d = Util.perimeter(new Circle(center, radius));
      assertEquals(50.26548, d, DELTA);
   }
   @Test
   public void testPerimCir2() {
      Point center = new Point(1, 2);
      double radius = 29;
      double d = Util.perimeter(new Circle(center, radius));
      assertEquals(182.21237, d, DELTA);
   }

   @Test
   public void testPerimRect() {
      Point topLeft = new Point(0.0, 5.0);
      Point bottomRight = new Point(3.0, 0.0);
      double d = Util.perimeter(new Rectangle(topLeft, bottomRight));
      assertEquals(16.0, d, DELTA);
   }
   @Test
   public void testPerimRect2() {
      Point topLeft = new Point(1.0, 5.0);
      Point bottomRight = new Point(3, 1.0);
      double d = Util.perimeter(new Rectangle(topLeft, bottomRight));
      assertEquals(12.0, d, DELTA);
   }

   @Test
   public void testPerimPoly() {
      List < Point >points = new ArrayList < Point >();
      points.add(new Point(0, 0));
      points.add(new Point(3,0));
      points.add(new Point(0,4));
      double d = Util.perimeter(new Polygon(points));
      assertEquals(12.0, d, DELTA);
   }
   @Test
   public void testPerimPoly2() {
      List < Point >points = new ArrayList < Point >();
      points.add(new Point(0, 0));
      points.add(new Point(3,0));
      points.add(new Point(8,2));
      points.add(new Point(10,5));
      double d = Util.perimeter(new Polygon(points));
      assertEquals(23.17105, d, DELTA);
   }

   @Test
   public void testCirGetRadius(){
      Circle cir = new Circle(new Point(3.4, 6.4), 6.4);
      double radius = cir.getRadius();
      assertEquals(6.4, radius, DELTA);
   }
   @Test
   public void testCirGetRadius2(){
      Circle cir = new Circle(new Point(8.2, 2.6), 26);
      double radius = cir.getRadius();
      assertEquals(26.0, radius, DELTA);
   }

   @Test
   public void testCirGetCenter(){
      Circle cir = new Circle(new Point(4.8, 8.8), 5.3);
      Point center = cir.getCenter();
      assertEquals(4.8, center.getX(), DELTA);
      assertEquals(8.8, center.getY(), DELTA);
   }
   @Test
   public void testCirGetCenter2(){
      Circle cir = new Circle(new Point(9.2, 0.0), 56);
      Point center = cir.getCenter();
      assertEquals(9.2, center.getX(), DELTA);
      assertEquals(0.0, center.getY(), DELTA);
   }

   @Test
   public void testRectGetBottomRight(){
      Rectangle rect = new Rectangle(new Point(0.0, 3.4), new Point(14.3, 0));
      Point bottomRight = rect.getBottomRight();
      assertEquals(14.3, bottomRight.getX(), DELTA);
      assertEquals(0, bottomRight.getY(), DELTA);
   }

   @Test
   public void testRectGetTopLeft(){
      Rectangle rect = new Rectangle(new Point(0.0, 8.3), new Point(10.3, 0));
      Point topLeft = rect.getTopLeft();
      assertEquals(0, topLeft.getX(), DELTA);
      assertEquals(8.3, topLeft.getY(), DELTA);
   }

   @Test
   public void testPolyGetPoints() {
      List < Point >points = new ArrayList < Point >();
      points.add(new Point(0, 0));
      points.add(new Point(4,6));
      points.add(new Point(9,3));
      Polygon poly = new Polygon(points);
      Polygon poly2 = new Polygon(points);
      for (int i = 0; i < poly.getPoints().size(); i++) {
         assertEquals(poly2.getPoints().get(i).getX(), poly.getPoints().get(i).getX(), DELTA);
         assertEquals(poly2.getPoints().get(i).getY(), poly.getPoints().get(i).getY(), DELTA);
      }
   }

   @Test
   public void testWhichIsBigger(){
      Circle cir = new Circle(new Point(2.3, 4.7), 6.2);
      Rectangle rect = new Rectangle(new Point(0.0, 6.9), new Point(6.3, 0));
      List < Point >points = new ArrayList < Point >();
      points.add(new Point(0, 0));
      points.add(new Point(2.3,4.6));
      points.add(new Point(7.2,6.3));
      Polygon poly = new Polygon(points);
      double d = Bigger.whichIsBigger(cir, rect, poly);
      assertEquals(38.95574, d, DELTA);
   }
   @Test
   public void testWhichIsBigger2(){
      Circle cir = new Circle(new Point(0.0, 0.0), 41);
      Rectangle rect = new Rectangle(new Point(4.3, 23.5), new Point(10.3, 2.3));
      List < Point >points = new ArrayList < Point >();
      points.add(new Point(0, 0));
      points.add(new Point(11.2,3.1));
      points.add(new Point(7.2,9.3));
      Polygon poly = new Polygon(points);
      double d = Bigger.whichIsBigger(cir, rect, poly);
      assertEquals(257.61059, d, DELTA);
   }


   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
