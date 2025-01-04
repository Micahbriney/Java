import java.util.*;
/**
 * A game of Nim.
 *
 * @author Workman/Hatalsky
 * @version 1.0
 */
public class Game
{
   public static void main(String[] args)
   {
      Player p1, p2;
      Pile pile;
      String name;
      int numSticks;
      boolean done = false;
      Scanner in = new Scanner(System.in);

      System.out.print("Player 1's name? ");
      name = in.next();
      p1 = new Player(name);

      System.out.print("Player 2's name? ");
      name = in.next();
      p2 = new Player(name);

      System.out.print("How many sticks? ");
      numSticks = in.nextInt();
      pile = new Pile(numSticks);
        
      while(!done)
      {
         p1.takeTurn(pile);
         System.out.println(p1 + " takes " + p1.sticksTaken() + " sticks.\n" +
          "There are " + pile.sticks() + " left in the pile.");
            
         if(pile.sticks()<=0)
         {
            done=true;
            System.out.println(p2 +" is the winner");
         } 
         else
         {
            p2.takeTurn(pile);
            System.out.println(p2 + " takes " + p2.sticksTaken() + " sticks.\n" +
             "There are " + pile.sticks() + " left in the pile.");
            if(pile.sticks()<=0)
            {
               done=true;
               System.out.println(p1 + " is the winner");
            }
         }    
      }
   }
}
