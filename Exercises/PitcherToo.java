/* A class designed just to hold data (like a C-struct) */
public class PitcherToo
{
    public static final int INNINGS_PER_GAME = 9;

    public double inningsPitched;
    public int runsScored;
    public int wins;
    public int losses;

    public PitcherToo(double inningsPitched, int runsScored, int wins,
                   int losses)
    {
        this.inningsPitched = inningsPitched;
        this.runsScored = runsScored; 
        this.wins = wins; 
        this.losses = losses;
    }
}