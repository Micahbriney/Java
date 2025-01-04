import java.util.*;

public final class CommunityLSim {

  	ArrayList<Player> players;
  	private int numPeeps;
  	private Random random = new Random();
	private HashSet<Integer> curPlayers;
	private HashSet<Integer> schoolarshipWinners;
  	private Game game;
  	//you will need to add more instance variables

	public CommunityLSim( int numP) {
		numPeeps = numP;
		//create the players
		players = new ArrayList<Player>();
		curPlayers = new HashSet<Integer>();
		schoolarshipWinners = new HashSet<Integer>();
		game = new Game();

		//generate a community of 30
		for (int i = 0; i < numPeeps; i++) {
			if (i < numPeeps/2.0)
				players.add(new Player(PlayerKind.POORLY_PAID, (float)(99+Math.random())));
			else
				players.add(new Player(PlayerKind.WELL_PAID, (float)(100.1+Math.random())));
		}

	}
	public int getSize() {
			return numPeeps;
		}

	public Player getPlayer(int i) {
			return players.get(i);
		}

	public void addPocketChange(int year) {
		for (Player p : players) {
			if (p.getKind() == PlayerKind.WELL_PAID){
				p.setMoney(0.1f);
			}
			else{
				p.setMoney(0.03f);
			}
		}
	}

	private void redistributeWealth(){
		if(!schoolarshipWinners.isEmpty()){
			schoolarshipWinners.clear();
		}

		randomUniqIndx((int)(players.size() / 2 * 0.3), 0 , 14, schoolarshipWinners);
		randomUniqIndx((int)(players.size() / 2 * 0.7), 15 , 29, schoolarshipWinners);

		return;
	}

	private void reDoWhoPlays() {
		// Clear previous year players
		if(!curPlayers.isEmpty()){
			curPlayers.clear();
		}

		randomUniqIndx((int)(players.size() / 2 * 0.6), 0 , 14, curPlayers);		// Add Poor
		randomUniqIndx((int)(players.size() / 2 * 0.4), 15 , 29, curPlayers);		// Add Wealthy

		return;
	}

	/* generate some random indices for who might play the lottery
		in a given range of indices */
	private void randomUniqIndx(int numI, int startRange, int endRange, HashSet<Integer> hs) {
		for (int i = 0; i < numI; i++){
			hs.add(random.nextInt(endRange - startRange + 1) + startRange);

		}
		return;
	}

	private void printMinMaxMoney(int year, float mostMoney, float leastMoney){
		System.out.println("After year " + year + ":" +
				"\nThe person with the most money has: " + mostMoney +
				"\nThe person with the least money has: " + leastMoney);
	}

	public void simulateYears(int numYears) {
		/*now simulate lottery play for some years */
		for (int year=0; year < numYears; year++) {
			float mostMoney = 0;
			float leastMoney = 102;
			addPocketChange(year);
			reDoWhoPlays();
			game.winningLotNumber();
			redistributeWealth();

//
//			// Pick one random schoolarship winner
//			int rand = new Random().nextInt(schoolarshipWinners.size());
//			System.out.println(rand);
//			Iterator<Integer> iter = schoolarshipWinners.iterator();
//			for (int i = 0; i < rand; i++) {	// Iterates to element before rand
//				iter.next();
//			}
//			Player schoolarshipWinner = players.get(iter.next());	//Iterate to randomly picked element
//			schoolarshipWinner.setMoney(1.0f);

			for (Integer playerIndex : schoolarshipWinners) {
				Player p = players.get(playerIndex);
				p.setMoney(1.0f);
			}

			for (Integer playerIndex : curPlayers) {
				Player p = players.get(playerIndex);
				p.playRandom();
				p.setMoney(game.playResult(game.numMatch(p.getLotteryNums())));
			}

			for (Player p : players) {
				if (p.getMoney() > mostMoney)
					mostMoney = p.getMoney();
				if (p.getMoney() < leastMoney)
					leastMoney = p.getMoney();
				p.updateMoneyEachYear();
			}
			printMinMaxMoney(year, mostMoney, leastMoney);
		}
  }

}
