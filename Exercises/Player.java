
//TODO - show quickly - question about sticksTaken = 0 - show pile passed in...
// demo program
//what do you think that is?  every object is an Object.  Object has toString method implicitly called.
//can override it to give better implementation for our more specific object.
//toString()
//what might be benefit to having toString() method in Object I may or may not override?

/**
 * A player in the game simple Nim.
 */
public class Player 
{  
   // Instance Variables:
   private int sticksTaken;
   private String name;  
  
   /**
    * Create a new Player with the specified name.
    *
    * @param name the name of the Player
    */
   public Player(String name) {
      this.name = name;
      sticksTaken = 0;
   }
  
   /**
    * String representation of the Player
    *
    * @return the name of the Player
    */
   public String toString() { 
      return name;
   }
  
   /**
    * The number of sticks this Player removed on this Player's most recent turn:
    * 1, 2, or 3. Returns 0 if this Player has not yet taken a turn.
    *
    * @return the number of sticks removed or 0
    */
   public int sticksTaken () { 
      return sticksTaken;
   }
  
   /**
    * Remove 1, 2, or 3 sticks from the specified Pile.
    * The Pile must not be empty.
    *
    * @param pile the pile to remove sticks from
    */
   public void takeTurn (Pile pile) 
   {
      sticksTaken = 1;
      pile.remove(sticksTaken);
   }
}






