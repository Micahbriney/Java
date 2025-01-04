public class PitcherTooMain
{
   public static void main (String[] args)
   {
      PitcherToo p1 = new PitcherToo(6, 3, 1, 0);
      
      System.out.println("ERA of Pitcher 1 is " + PitcherTooUtil.ERA(p1));
      
      System.out.println("Wins: " + p1.wins);
      p1.wins++;
      System.out.println("Wins: " + p1.wins);
   }
}