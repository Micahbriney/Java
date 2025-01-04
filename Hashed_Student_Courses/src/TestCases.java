import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.time.LocalTime;

import org.junit.Test;

public class TestCases
{
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExerciseFalse2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CS", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "03", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 5,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse5()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse6()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 1), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse7()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(1, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse8()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 1));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse9()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CS", "20", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse10()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "20", 3,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse11()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 3,
              LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }





   //******************************** NULLS ************************************************

   @Test
   public void testExerciseFalse12()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection(null, "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse13()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", null, 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse14()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              null, LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse15()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), null);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse16()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection(null, null, 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse17()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", null, 35,
              null, LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse18()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
              null, null);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse19()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection(null, null, 35,
              null, LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse20()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection(null, null, 35,
              LocalTime.of(9, 10), null);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse21()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection("CSC", null, 35,
              null, null);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExerciseFalse22()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(2, 0));
      final CourseSection two = new CourseSection(null, "203", 35,
              null, null);

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

}
