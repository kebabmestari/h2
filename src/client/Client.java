package client;

import shared.ServerCommunication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
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

    // server communicaton stub
    private static ServerCommunication comm;

    /**
     * Application entry point
     */
    public static void main(String[] args) {

        RenderService.renderMessage("Welcome to " + appName);

        Scanner cin = new Scanner(System.in);

        System.out.println("Server address:");
        String addr = cin.next();

        System.setSecurityManager(new RMISecurityManager());

        System.out.println("Trying to connect to " + addr);

        // connect to server
        try {
            comm = (ServerCommunication) Naming.lookup("rmi://" + addr + "");
        } catch (MalformedURLException e) {
            System.err.println("Invalid url");
            exit(1);
        } catch (RemoteException e) {
            System.err.println("Failed to connect");
            exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            exit(1);
        }

        runGame(cin);

        System.out.println("Exiting..");
    }

    /**
     * Main game
     */
    public static void runGame(Scanner cin) {

        System.out.println("Waiting the game to start..");
        while(!gameOn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String action = "";
        while (!(action = cin.next()).equals("q")) {

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
