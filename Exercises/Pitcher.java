public class Pitcher
{
	/* static data */
	public static final int INNINGS_PER_GAME = 9;
	/* instance variables - the data */
	private double inningsPitched;
	private int runsScored;
	private int wins;
	private int losses;

//---------------------------------------------------------------
/* Default and regular constructors */
public Pitcher()
{
}

// Similar to __init__ in Python
public Pitcher(double inningsPitched, int runsScored, int wins, int losses)
{
	this.inningsPitched = inningsPitched;
	this.runsScored = runsScored;
	this.wins = wins;
	this.losses = losses;
}

//---------------------------------------------------------------
/* instance method to query for the Earned Run Average */
public double ERA()
{
	if (inningsPitched > 0)
	return runsScored/inningsPitched*INNINGS_PER_GAME;
	return 0.0;
}

// Accessor methods
public double inningsPitched() { return inningsPitched; }
public int runsScored() {return runsScored;}
public int wins() {return wins;}
public int losses() {return losses;}
public void incrementWins() { wins++; }
}
