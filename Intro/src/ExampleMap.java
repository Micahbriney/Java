import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
   public static List<String> highEnrollmentStudents(
      Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */

      for (Map.Entry<String, List<Course>> entry : courseListsByStudentName.entrySet())
      {
         int totalUnits = 0;
//         System.out.println(entry.getKey());
         for (int i = 0; i < entry.getValue().size(); i++)
         {
//            System.out.println(entry.getValue().get(i).getNumUnits());
            totalUnits += entry.getValue().get(i).getNumUnits();
         }
//         System.out.println(totalUnits);
         if (totalUnits > unitThreshold)
         {
            overEnrolledStudents.add(entry.getKey());
         }
      }
//      System.out.println(overEnrolledStudents);

      return overEnrolledStudents;      
   }
}
