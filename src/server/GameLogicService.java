package server;

import java.util.Random;

/**
 * Static methods to enclose the TicTacToe game logic and closely related functionalities
 * Created by adminpc on 24/1/2017.
 */
public class GameLogicService {
    /**
     * Get the starting player id
     * @return 0 or 1
     */
    public static int getStartingPlayer() {
        System.out.print("Randomizing the starting player: ");
        final int result = (new Random()).nextInt(2);
        System.out.println(result);
        return result;
    }

    public static
}
