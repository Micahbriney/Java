public class PitcherMain
{
   public static void main (String[] args)
   {
      System.out.println("IPG: " + Pitcher.INNINGS_PER_GAME);

      // Create 2 "instances" of the Pitcher class
      // Create 2 objects of type Pitcher
      Pitcher p1 = new Pitcher();
      Pitcher p2 = new Pitcher(6, 3, 1, 0);
      
      System.out.println("ERA of Pitcher 1 is " + p1.ERA());
      System.out.println("ERA of Pitcher 2 is " + p2.ERA());
           
      System.out.println("Wins: " + p1.wins());
      p1.incrementWins();
      System.out.println("Wins: " + p1.wins());

      System.out.println("IPG: " + p1.INNINGS_PER_GAME);
      System.out.println("IPG: " + Pitcher.INNINGS_PER_GAME);
      
      // p2.INNINGS_PER_GAME = 6; Can't do.  Why?  What if we could?
      // System.out.println("IPG: " + p1.INNINGS_PER_GAME);
      
      // p1.inningsPitched = 12.0  Can't do.  Why?
   }
}














