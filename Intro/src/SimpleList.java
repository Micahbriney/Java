import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

class SimpleList
{
   public static List<Integer> squareAll(List<Integer> values)
   {
      List<Integer> newValues = new LinkedList<Integer>();

      /* TO DO: The output list, newValues, should hold as
         its elements the square of the corresponding element
         in the input list.

         Write a loop to add the square of each element from the
         input list into the output list.  Use a "foreach".
      */


       // for loop
//      for(int i = 0; i < values.size(); i++)
//      {
//         newValues.add(values.get(i) * values.get(i));
//      }

      // for each loop
      for(Integer val: values)
      {
         newValues.add(val * val);
      }

      // Iterator
//      Iterator<Integer> it = values.iterator();
//      while (it.hasNext())
//      {
//         Integer root = it.next();
//         newValues.add((int) Math.pow(root.doubleValue(), 2.0));
//      }

      // While Loop
//      int i = 0;
//      while (values.size() > i)
//      {
//         newValues.add(values.get(i) * values.get(i));
//         i++;
//      }

      return newValues;
   }
}
