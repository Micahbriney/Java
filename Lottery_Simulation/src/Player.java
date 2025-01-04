import java.util.*;

class Player {
	private static final int MAX = 42;
	private PlayerKind kind;
	private float money;
	private ArrayList<Float> moneyOverTime;
    Random random = new Random();
	private int red, green, blue;

	private HashSet<Integer> lotteryNums;

	//constructor
	public Player(PlayerKind pK, float startFunds) {
		kind = pK;
		money = startFunds;
		moneyOverTime = new ArrayList<Float>();
		moneyOverTime.add(startFunds);
		red = random.nextInt(100);
		green = random.nextInt(100);
		blue = random.nextInt(100);
		lotteryNums = new HashSet<Integer>();

		//overall blue tint to POORLY_PAID	
		if (kind == PlayerKind.WELL_PAID) {
			red += 100;
		} else {
			blue += 100;
		}
	}

	// generate 5 random numbers
	public void playRandom(){
		if(!lotteryNums.isEmpty()){
			lotteryNums.clear();
		}
		while(lotteryNums.size() < 5){
			lotteryNums.add(random.nextInt(MAX) + 1);
		}
		return;
	}

	public int getR() { return red; }
	public int getG() { return green; }
	public int getB() { return blue; }
	public float getMoney() { return money; }
	public PlayerKind getKind() { return kind; }
	public ArrayList<Float> getFunds() { return moneyOverTime; }
	public void setMoney(float money) {
		this.money += money;
	}
	public void updateMoneyEachYear() {
		moneyOverTime.add(money);
	}
	public HashSet<Integer> getLotteryNums() {
		return lotteryNums;
	}
}
