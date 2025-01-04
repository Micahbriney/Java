public class PitcherTooUtil
{
	public static double ERA(PitcherToo p)
    {
       if (p.inningsPitched > 0)
          return p.runsScored/p.inningsPitched*PitcherToo.INNINGS_PER_GAME;

       return 0.0;
    }
}