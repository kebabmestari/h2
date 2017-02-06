package server;

import java.util.Random;

/**
 * Static methods to enclose the TicTacToe game logic and closely related functionalities
 * Created by adminpc on 24/1/2017.
 */
public class GameLogicService {

    // maximum number of players
    public static final int MAXPLAYERS = 2;

    // how many needed for victory
    public static final int howManyInRow = 3;

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

        boolean emptySquaresLeft = false;

        // iterate through each coordinate and check sitation
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if(board[y][x] == 0)
                    emptySquaresLeft = true;
                if(didWin(board, x, y)) {
                    return board[y][x];
                }
            }
        }

        // if no emptry squares left and nobody won it means a stalemate
        if(!emptySquaresLeft) return 3;

        // otherwise neutral message
        return 0;
    }

    /**
     * Check individual coordinate's win sitation
     * Scales up!
     * @param board board array
     * @param x x coordinate
     * @param y y coordinate
     * @return true if this coordinate 'won'
     */
    private static boolean didWin(int[][] board, int x, int y) {
        int currentCode = board[y][x];
        if(currentCode == 0) return false;

        int prevX = x - 1;
        int prevY = y - 1;
        int nextX = x + 1;
        int nextY = y + 1;

        // diagonal 1
        int diagRow1 = 0;
        // diagonal 2
        int diagRow2 = 0;
        // horizontal row
        int horRow = 0;
        // vertical row
        int verRow = 0;

        // to the left
        while(prevX >= 1) {
            if(board[y][prevX] == currentCode) {
                horRow++;
                prevX--;
            } else {
                break;
            }
        }

        // to the right
        while(nextX < board[y].length) {
            if(board[y][nextX] == currentCode) {
                horRow++;
                nextX++;
            } else {
                break;
            }
        }

        // up
        while(prevY >= 0) {
            if(board[prevY][x] == currentCode) {
                verRow++;
                prevY--;
            } else {
                break;
            }
        }

        // down
        while(nextY < board.length) {
            if(board[nextY][y] == currentCode) {
                verRow++;
                nextY++;
            } else {
                break;
            }
        }

        // reset and check diagonals
        prevX = x - 1;
        prevY = y - 1;
        nextX = x + 1;
        nextY = y + 1;

        // right down
        while(nextX < board[0].length && nextY < board.length) {
            if(board[nextY][nextX] == currentCode) {
                diagRow1++;
                nextX++;
                nextY++;
            } else {
                break;
            }
        }

        // left up
        while(prevX >= 0 && prevY > 0) {
            if(board[prevY][prevX] == currentCode) {
                diagRow1++;
                nextY--;
                nextX--;
            } else {
                break;
            }
        }

        prevX = x - 1;
        prevY = y - 1;
        nextX = x + 1;
        nextY = y + 1;

        // right up
        while(nextX < board[0].length && prevY > 0) {
            if(board[prevY][nextX] == currentCode) {
                diagRow2++;
                prevY--;
                nextX++;
            } else {
                break;
            }
        }

        // left down
        while(prevX >= 0 && nextY < board.length) {
            if(board[nextY][prevX] == currentCode) {
                diagRow2++;
                prevX--;
                nextY++;
            } else {
                break;
            }
        }

        if((1 + horRow) >= GameLogicService.howManyInRow) return true;
        if((1 + verRow) >= GameLogicService.howManyInRow) return true;
        if((1 + diagRow1) >= GameLogicService.howManyInRow) return true;
        if((1 + diagRow2) >= GameLogicService.howManyInRow) return true;
        return false;
    }

}
