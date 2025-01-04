import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */
   //***************************       CIRCLE        ******************************************************************

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(101.2839543, c.getArea(), DELTA);
   }

   @Test
   public void testCircleGetRadius()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(5.678, c.getRadius(), DELTA);
   }

   @Test
   public void testCircleSetRadius(){
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.setRadius(12.42);
      assertEquals(12.42, c.getRadius(), DELTA);
   }

   @Test
   public void testCircleGetCenter(){
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(2, c.getCenter().x, DELTA);
      assertEquals(3, c.getCenter().y, DELTA);
   }

   @Test
   public void testCircleEqualsTrue(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertTrue(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse1(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(6.678, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse2(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(5.678, new Point(1, 2), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse3(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(5.678, new Point(2, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse4(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(6.678, new Point(1, 2), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse5(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(5.678, new Point(1, 2), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse6(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(6.678, new Point(1, 2), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse7(){
      Rectangle a = new Rectangle(5.678, 4.5, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(6.678, new Point(1, 2), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleEqualsFalse8(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle b = new Circle(6.678, new Point(1, 2), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testCircleGetColor(){
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(Color.BLACK, c.getColor());
   }

   @Test
   public void testCircleSetColor(){
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.setColor(Color.RED);

      assertEquals(Color.RED, c.getColor());
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }

   @Test
   public void testCircleTranslate()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.translate(new Point(4, 1));


      assertEquals(6.0, c.getCenter().x, DELTA);
      assertEquals(4.0, c.getCenter().y, DELTA);
   }

   //***************************      RECTANGLE      ******************************************************************

   @Test
   public void testRectangleGetArea()
   {
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(285.36, c.getArea(), DELTA);
   }

   @Test
   public void testRectangleGetWidth()
   {
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(12.3, c.getWidth(), DELTA);
   }

   @Test
   public void testRectangleGetHeight()
   {
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(23.2, c.getHeight(), DELTA);
   }

   @Test
   public void testRectangleSetWidth(){
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      c.setWidth(22.6);
      assertEquals(22.6, c.getWidth(), DELTA);
   }

   @Test
   public void testRectangleSetHeight(){
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      c.setHeight(78.3);
      assertEquals(78.3, c.getHeight(), DELTA);
   }

   @Test
   public void testRectangleGetTopLeft(){
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(2, c.getTopLeft().x, DELTA);
      assertEquals(3, c.getTopLeft().y, DELTA);
   }

   @Test
   public void testRectangleEqualsTrue(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertTrue(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse1(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.2, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse2(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.0, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse3(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.2, new Point(0, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse4(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.2, new Point(2, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse5(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.0, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse6(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.2, new Point(0, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse7(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.2, new Point(2, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse8(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.0, new Point(2, 0), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse9(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.0, new Point(2, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse10(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.0, 23.0, new Point(0, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse11(){
      Circle a = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testRectangleEqualsFalse12(){
      Rectangle a = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      Rectangle b = null;

      assertFalse(a.equals(b));
   }


   @Test
   public void testRectangleGetColor(){
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(Color.BLACK, c.getColor());
   }

   @Test
   public void testRectangleSetColor(){
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      c.setColor(Color.RED);

      assertEquals(Color.RED, c.getColor());
   }

   @Test
   public void testRectangleGetPerimeter()
   {
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertEquals(71, c.getPerimeter(), DELTA);
   }

   @Test
   public void testRectangleTranslate()
   {
      Rectangle c = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);
      c.translate(new Point(4, 1));


      assertEquals(6.0, c.getTopLeft().x, DELTA);
      assertEquals(4.0, c.getTopLeft().y, DELTA);
   }


   //**********************************      TRIANGLE    **************************************************************

   @Test
   public void testTriangleGetArea()
   {
      Triangle c = new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertEquals(2.5, c.getArea(), DELTA);
   }

   @Test
   public void testTriangleGetVertexs()
   {
      Triangle c = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertEquals(0, c.getVertexA().x, DELTA);
      assertEquals(0, c.getVertexA().y, DELTA);
      assertEquals(6, c.getVertexB().x, DELTA);
      assertEquals(0, c.getVertexB().y, DELTA);
      assertEquals(3, c.getVertexC().x, DELTA);
      assertEquals(4, c.getVertexC().y, DELTA);
   }


   @Test
   public void testTriangleEqualsTrue(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertTrue(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse1(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(1, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse2(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 0), new Point(7, 0), new Point(3, 4), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse3(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 0), new Point(6, 0), new Point(8, 4), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse4(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse5(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 1), new Point(6, 2), new Point(3, 4), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse6(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(0, 1), new Point(6, 0), new Point(3, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse7(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(1, 0), new Point(6, 0), new Point(3, 4), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse8(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(1, 0), new Point(2, 0), new Point(3, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse9(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(1, 0), new Point(2, 0), new Point(3, 4), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse10(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = new Triangle(new Point(1, 0), new Point(2, 0), new Point(3, 3), Color.RED);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse11(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Rectangle b = new Rectangle(12.3, 23.2, new Point(2, 3), Color.BLACK);

      assertFalse(a.equals(b));
   }

   @Test
   public void testTriangleEqualsFalse12(){
      Triangle a = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);
      Triangle b = null;

      assertFalse(a.equals(b));
   }


   @Test
   public void testTriangleGetColor(){
      Triangle c = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertEquals(Color.BLACK, c.getColor());
   }

   @Test
   public void testTriangleSetColor(){
      Triangle c = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      c.setColor(Color.RED);

      assertEquals(Color.RED, c.getColor());
   }

   @Test
   public void testTriangleGetPerimeter()
   {
      Triangle c = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      assertEquals(16.0, c.getPerimeter(), DELTA);
   }

   @Test
   public void testTriangleTranslate()
   {
      Triangle c = new Triangle(new Point(0, 0), new Point(6, 0), new Point(3, 4), Color.BLACK);

      c.translate(new Point(4, 1));


      assertEquals( 4, c.getVertexA().x, DELTA);
      assertEquals( 1, c.getVertexA().y, DELTA);
      assertEquals(10, c.getVertexB().x, DELTA);
      assertEquals( 1, c.getVertexB().y, DELTA);
      assertEquals( 7, c.getVertexC().x, DELTA);
      assertEquals( 5, c.getVertexC().y, DELTA);
   }

//**********************************      WORKSPACE    **************************************************************

   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK));

      assertEquals(110.79060640845638, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testgetPerimeterOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK));

      assertEquals(59.898271811833865, ws.getPerimeterOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceSize()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK));
      ws.add(new Rectangle(3, 4, new Point(1, 3), Color.RED));
      ws.add(new Circle(7, new Point(4, 6), Color.BLACK));
      ws.add(new Triangle(new Point(1, 8), new Point(3, 8), new Point(5, 8), Color.BLACK));

      assertEquals(6, ws.size(), DELTA);
   }

   @Test
   public void testWorkSpaceGetShapesByColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> sc = new ArrayList<Shape>();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK));
      ws.add(new Rectangle(3, 4, new Point(1, 3), Color.RED));
      ws.add(new Circle(7, new Point(4, 6), Color.BLACK));
      ws.add(new Triangle(new Point(1, 8), new Point(3, 8), new Point(5, 8), Color.BLACK));

      sc.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      sc.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      sc.add(new Triangle(new Point(5, 3), new Point(6, 0), new Point(3, 4), Color.BLACK));
      sc.add(new Circle(7, new Point(4, 6), Color.BLACK));
      sc.add(new Triangle(new Point(1, 8), new Point(3, 8), new Point(5, 8), Color.BLACK));

      assertEquals(sc, ws.getShapesByColor(Color.BLACK));
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new ArrayList<Circle>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                 Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }


   @Test
   public void testWorkSpaceGetTriangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Triangle> expected = new ArrayList<Triangle>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Triangle c1 = new Triangle(new Point(2, 3), new Point(1, 3), new Point(7, 3), Color.BLACK);
      Triangle c2 = new Triangle(new Point(2, 10), new Point(2, 5), new Point(2, 8), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getTriangles());
   }


   @Test
   public void testWorkSpaceGetRectangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Rectangle> expected = new ArrayList<Rectangle>();


      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Rectangle.
      Rectangle r1 = new Rectangle(1.4, 3.2, new Point(7, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(3.2, 5.8, new Point(2, 8), Color.RED);

      ws.add(new Triangle(new Point(22, 10), new Point(12, 5), new Point(42, 8), Color.RED));
      ws.add(r1);
      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(r2);

      expected.add(r1);
      expected.add(r2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getRectangles());
   }

   @Test
   public void testWorkSpaceGet()
   {
      WorkSpace ws = new WorkSpace();
      Rectangle expected;


      Rectangle r1 = new Rectangle(1.4, 3.2, new Point(7, 3), Color.BLACK);
      Rectangle r2 = new Rectangle(3.2, 5.8, new Point(2, 8), Color.RED);

      ws.add(new Triangle(new Point(22, 10), new Point(12, 5), new Point(42, 8), Color.RED));   //Index 0
      ws.add(r1);
      ws.add(new Circle(1.11, new Point(-5, -3), Color.RED));
      ws.add(r2);    //Index 3

      expected = r2;

      assertEquals(expected, ws.get(3));
   }


   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, 
         new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
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
