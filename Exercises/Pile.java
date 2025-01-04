/**
 * A pile of sticks for playing simple Nim.
 */
public class Pile
{
   private int sticks;

   /**
    * Create a new Pile, with the specified number of sticks.
    * sticks must be non-negative.
    *
    * @param sticks the starting number of sticks for the pile
    */
   public Pile (int sticks)
   {
      this.sticks = sticks;
   }

  /**
   * @return The number of sticks remaining in this Pile.
   */
   public int sticks() {
      return sticks;
   }

   /**
    * Reduce the number of sticks by the specified amount.
    * numSticks must be non-negative and not greater than
    * this.sticks().
    *
    * @param numSticks the number of sticks to remove
    */
   public void remove (int numSticks)
   {
      sticks -= numSticks;

   }
}
