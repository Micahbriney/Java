/*examples to work on day 2 of 203 - actual data on size of CSC frosh class with one year retention data */
import java.util.HashMap;
import java.util.Map;

public class CompareCohorts {
   public static void main(String[] args) {

      csCohort year1 = new csCohort(2012, 132, .932);
      csCohort year2 = new csCohort(2013, 172, .924);
      csCohort year3 = new csCohort(2014, 157, .936);
      csCohort year4 = new csCohort(2015, 172, .977);

      System.out.println("The number of students still enrolled after one"
          + " year: " +  year1.retained() + " in " + year1.getYear());
      System.out.println("The number of students still enrolled after one"
          + " year: " +  year2.retained() + " in " + year2.getYear());
      System.out.println("The number of students still enrolled after one"
          + " year: " +  year3.retained() + " in " + year3.getYear());
      System.out.println("The number of students still enrolled after one"
          + " year: " +  year4.retained() + " in " + year4.getYear());

      //example map
      Map<String, csCohort> theCohorts = new HashMap<>();

      //put the data into the map
      theCohorts.put("year1", year1); 
      theCohorts.put("year2", year2); 
      theCohorts.put("year3", year3); 
      theCohorts.put("year4", year4); 

      // iterate through the map with a for each loop
      for (Map.Entry<String, csCohort> entry : theCohorts.entrySet() ) {
         if (entry.getValue().retained() > 150) {
            System.out.println("More then 150 students for: " + 
                                entry.getKey());
            System.out.println("which is: " + entry.getValue().getYear());
         }
      }  

        // another way to do the same thing
      for (String key : theCohorts.keySet())
      {
         if (theCohorts.get(key).retained() > 150) 
         {
            System.out.println("More then 150 students for: " + key);
            System.out.println("which is: " + theCohorts.get(key).getYear());
         }
      }
   }
}
