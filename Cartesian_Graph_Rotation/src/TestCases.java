import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /*
    * This test is just to get you started.
    */
   @Test
   public void testGetX1()
   {
      assertEquals(1.0, new Point(1.0, 2.0).getX(), DELTA);
   }
   @Test
   public void testGetX2()
   {
      assertEquals(15.0, new Point(15.0, 23.0).getX(), DELTA);
   }
   @Test
   public void testGetX3()
   {
      assertEquals(12.0, new Point(12.0, 13.0).getX(), DELTA);
   }

   @Test
   public void testGetY1()
   {
      assertEquals(2.0, new Point(1.0, 2.0).getY(), DELTA);
   }
   @Test
   public void testGetY2()
   {
      assertEquals(23.0, new Point(15.0, 23.0).getY(), DELTA);
   }
   @Test
   public void testGetY3()
   {
      assertEquals(13.0, new Point(12.0, 13.0).getY(), DELTA);
   }

   @Test
   public void testGetRadius1()
   {
      assertEquals(5.0, new Point(3.0, 4.0).getRadius(), DELTA);
   }
   @Test
   public void testGetRadius2()
   {
      assertEquals(10.0, new Point(6.0, 8.0).getRadius(), DELTA);
   }
   @Test
   public void testGetRadius3()
   {
      assertEquals(25.0, new Point(7.0, 24.0).getRadius(), DELTA);
   }

   @Test
   public void testGetAngle1()
   {
      assertEquals(0, new Point(3.0, 0.0).getAngle(), DELTA);
   }
   @Test
   public void testGetAngle2()
   {
      assertEquals(0.5235987755982989, new Point(Math.sqrt(3)/2, 0.5).getAngle(), DELTA);
   }
   @Test
   public void testGetAngle3()
   {
      assertEquals(2.0943951023931957, new Point(-0.5, Math.sqrt(3)/2).getAngle(), DELTA);
   }
   @Test
   public void testGetAngle4()
   {
      assertEquals(-2.992702705980296, new Point(-20.0, -3.0).getAngle(), DELTA);
   }





   @Test
   public void testRotate90X1()
   {
      assertEquals(-4.0, new Point(3.0, 4.0).rotate90().getX(), DELTA);
   }
   @Test
   public void testRotate90Y1()
   {
      assertEquals(3.0, new Point(3.0, 4.0).rotate90().getY(), DELTA);
   }

   @Test
   public void testRotate90X2()
   {
      assertEquals(-8.0, new Point(-5.0, 8.0).rotate90().getX(), DELTA);
   }
   @Test
   public void testRotate90Y2()
   {
      assertEquals(-5.0, new Point(-5.0, 8.0).rotate90().getY(), DELTA);
   }

   @Test
   public void testRotate90X3()
   {
      assertEquals(3.0, new Point(-7.0, -3.0).rotate90().getX(), DELTA);
   }
   @Test
   public void testRotate90Y3()
   {
      assertEquals(-7.0, new Point(-7.0, -3.0).rotate90().getY(), DELTA);
   }


   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */

   @Test
   public void testImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getX",
         "getY",
         "getRadius",
         "getAngle",
         "rotate90"
         );

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class,
         double.class,
         double.class,
         double.class,
         Point.class
         );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0]
         );

      verifyImplSpecifics(Point.class, expectedMethodNames,
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
         0, Point.class.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertTrue("Unexpected number of public methods",
         expectedMethodNames.size()+1 >= publicMethods.size());

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
