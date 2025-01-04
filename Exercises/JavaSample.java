public class JavaSample
{
   // In Java, everything must be inside a class
   // When the class is run, the method below will run
   public static void main(String[] args) // 
   {
      // Every variable must have a type!
      int x = 5;
      double y = 12.3456;
      char letter = 'A';
      String make = "Santa Cruz";
      String model = "Bronson";
      int[] scores = {83, 42, 77, 92, 73, 95, 81, 42}; // Size is fixed!
      double z;  // Must be double - Math.pow returns a double
      
      x = 10;
      if (x == 10 && model.equals("Bronson")) {
         x = 34;
         System.out.println("Great bike!");
      } // Are the braces needed?

      // i can only be used (only has scope) in the for loop
      for (int i = 0; i < 8; i++) // Could also use scores.length
         System.out.println("Score " + i + ": " + scores[i]);
         
      // val can only be used (only has scope) in the for loop
      for (int val : scores) // scores is "iterable"
         System.out.println(val);
         
      int i = 0;  // The type of i must be declared.
      while (i < 8) {
         System.out.println(scores[i]);
         i++;  // Doesn't exist in Python
      }  // Braces here are very important!!! Why?
      
      char[] charArray = model.toCharArray(); // Show String javadoc
      for (char c : charArray)
         System.out.println(c);
      
      for (i = 0; i < model.length(); i++)    // String javadoc
         System.out.println(model.charAt(i)); // Can't index String
         
      z = Math.pow(x, 3); // No ** operator (also no // operator)
      System.out.println(z);
      
      printMult(model, 10);
      //printMult(10, 5); //Can't do, because of type checking
   }
   public static void printMult(String s, int n) // parameters must have types
   {
      for (int i = 0; i < n; i++)
         System.out.println(s);
   }
}
