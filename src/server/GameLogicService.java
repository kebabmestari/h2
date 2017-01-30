package server;

import java.util.Random;

/**
 * Static methods to enclose the TicTacToe game logic and closely related functionalities
 * Created by adminpc on 24/1/2017.
 */
public class GameLogicService {

    public static int MAXPLAYERS = 2;

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

    /**
     * Check for game state and see if the
     * game has ended
     * @return 0 if not ended, 1 or 2 represent the winner and 3 is stalemate
     */
    public static int getWinner(int[][] board) {

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {

            }
        }

        return 0;
    }

    private static boolean didWin(int[][] board) {

    }

}
