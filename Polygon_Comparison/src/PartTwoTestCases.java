import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartTwoTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testPerimCir() {
      Circle cir = new Circle(new Point(4.8, 8.8), 5.3);
      double cirPerim = cir.perimeter();
      assertEquals(33.30088, cirPerim, DELTA);
   }
   @Test
   public void testPerimCir2() {
      Circle cir = new Circle(new Point(0, 0), 24.8);
      double cirPerim = cir.perimeter();
      assertEquals(155.82299, cirPerim, DELTA);
   }

   @Test
   public void testPerimRect() {
      Rectangle rect = new Rectangle(new Point(0.0, 8.3), new Point(10.3, 0));
      double rectPerim = rect.perimeter();
      assertEquals(37.2, rectPerim, DELTA);
   }
   @Test
   public void testPerimRect2() {
      Rectangle rect = new Rectangle(new Point(1.0, 5.0), new Point(3, 1.0));
      double rectPerim = rect.perimeter();
      assertEquals(12, rectPerim, DELTA);
   }

   @Test
   public void testPerimPoly() {
      List < Point >points = new ArrayList< Point >();
      points.add(new Point(0, 0));
      points.add(new Point(3,0));
      points.add(new Point(0,4));
      Polygon poly = new Polygon(points);
      double polyPerim = poly.perimeter();
      assertEquals(12.0, polyPerim, DELTA);
   }
   @Test
   public void testPerimPoly2() {
      List < Point >points = new ArrayList< Point >();
      points.add(new Point(8, 2));
      points.add(new Point(4,9));
      points.add(new Point(0,4));
      points.add(new Point(10,1));
      Polygon poly = new Polygon(points);
      double polyPerim = poly.perimeter();
      assertEquals(27.14175, polyPerim, DELTA);
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
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
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
