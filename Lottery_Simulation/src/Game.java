import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Game {
    private static final int MAX = 42;
    private static final float TICKETCOST   = -1.0f;
    private static final float TWOMATCHES   = 1.0f;
    private static final float THREEMATCHES = 10.86f;
    private static final float FOURMATCHES  = 197.53f;
    private static final float FIVEMATCHES  = 212534.83f;
    private Set<Integer> winningLotteryNumber = new HashSet<Integer>();
    private Random random = new Random();

    public void winningLotNumber(){
        if (!winningLotteryNumber.isEmpty()) {
            winningLotteryNumber.clear();
        }

        while(winningLotteryNumber.size() < 5){
            winningLotteryNumber.add(random.nextInt(MAX) + 1);
        }
        return;
    }

    public int numMatch(HashSet<Integer> playerNums){
        int numMatchs = 0;
        for (Integer playerNum : playerNums){
            if (winningLotteryNumber.contains(playerNum))
                numMatchs++;
        }
        return numMatchs;

        //TODO Delete Tests
        //Tests Can Delete
//        HashSet<Integer> playerNumsTest = new HashSet<Integer>();
//        playerNumsTest.add(1);
//        playerNumsTest.add(2);
//        playerNumsTest.add(3);
//        playerNumsTest.add(4);
//        playerNumsTest.add(5);
//
//        HashSet<Integer> lotNumsTest = new HashSet<Integer>();
//        lotNumsTest.add(1);
//        lotNumsTest.add(2);
//        lotNumsTest.add(3);
//        lotNumsTest.add(6);
//        lotNumsTest.add(7);
//
//
//        // Test
//        for (Integer playerNum : playerNumsTest) {
////            System.out.println("Outer Loop" + playerNum);
//            for (Integer winNum : lotNumsTest) {
////                System.out.println("Inner Loop" + );
//                if (playerNum.equals(winNum)){
//                    match++;
//                    break;
//                }
//            }
//        }
//        return match;
    }

    public float playResult(int numMatches){
        float winnings = TICKETCOST;
        switch (numMatches){
            case 2:
                winnings += TWOMATCHES;
                break;
            case 3:
                winnings += THREEMATCHES;
                break;
            case 4:
                winnings += FOURMATCHES;
                break;
            case 5:
                winnings += FIVEMATCHES;
            break;
            default:
                winnings += 0;
            break;
        }
        return winnings;
    }

}
