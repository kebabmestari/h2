package client;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Client main class and entry point
 * Encloses main logic
 * Created by adminpc on 21/1/2017.
 */
public class Client {

    // application name
    public static final String appName = "JTicTacToe";

    // name of the client
    private String clientName;

    // game board status
    private static int[][] board;

    // game on flag
    private static boolean gameOn = false;

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        RenderService.renderMessage("Welcome to " + appName);
        Scanner cin = new Scanner(System.in);
        System.out.println("Server address:");
        String addr = cin.next();
        System.out.println("Server port:");
        String pport = cin.next();
        int port;
        try {
            port = Integer.parseInt(pport);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port");
            exit(1);
        }

    }

    /**
     * @return true if game is on and actions can be sent
     */
    public static boolean isGameOn() {
        return gameOn;
    }

    /**
     * @param gameOn
     */
    public static void setGameOn(boolean gameOn) {
        Client.gameOn = gameOn;
    }

    /**
     * @return board status array
     */
    public static int[][] getBoard() {
        return board;
    }

    /**
     * Set board array
     * @param board
     */
    public static void setBoard(int[][] board) {
        board = board;
    }

    /**
     * Exit application
     * @param code exit status
     */
    public static void exit(int code) {
        System.out.println("Exiting..");
        System.exit(code);
    }

    /**
     * Exit with no error
     */
    public static void exit() {
        exit(0);
    }
}
