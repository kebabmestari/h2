package client;

import server.ServerCommunicationImpl;
import shared.ClientCommunication;
import shared.ServerCommunication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
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
    // turn flag
    private static boolean myTurn = false;

    // the side id, represents the player on board
    private static int side;

    // server communicaton stub
    private static ServerCommunication comm;
    private static ClientCommunicationImpl clientComm;

    // player name
    private static String name;

    /**
     * Application entry point
     */
    public static void main(String[] args) {

        RenderService.renderMessage("Welcome to " + appName);

        Scanner cin = new Scanner(System.in);

        System.out.println("Server address:");
        String addr = cin.next();

        System.out.println("Trying to connect to " + addr);

        // connect to server
        try {
            comm = (ServerCommunication) Naming.lookup("rmi://" + addr + "/comm");
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

        try {
            clientComm = new ClientCommunicationImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Give your name:");
        name = cin.next();

        try {
            joinRoom(cin);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Failed to join a room");
            exit(1);
        }

        runGame(cin);

        System.out.println("Exiting..");
    }

    public static void joinRoom(Scanner cin) throws RemoteException {
        System.out.println("Getting open rooms..");
        try {
            for(String room: comm.requestOpenRooms()) {
                System.out.println(room);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        while(true) {
            System.out.println("\nJoin room: ");
            String roomName = cin.next();
            if(comm.joinRoom(clientComm, name, roomName)) break;
        }
        System.out.println("Joined the room");
    }

    /**
     * Main game
     */
    public static void runGame(Scanner cin) {

        while(!gameOn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String action = "";
        while (!(action = cin.next()).equals("q")) {
            if(isMyTurn()) {
                int[] coords = parseMove(action);
                try {
                    // submit move into server
                    if(comm.makeMove(name, coords)) {
                        System.out.println("Move successfull");
                    } else {
                        System.err.println("Move could not be made, is it your turn?");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Parse an input string into two coordinates
     * @param str input string
     * @return array of two ints which represent X and Y coordinates
     */
    public static int[] parseMove(String str) {
        Pattern pattern = Pattern.compile("(\\d{1,2})\\s*,?\\s*(\\d{1,2})");
        Matcher matcher = pattern.matcher(str);
        int[] result = new int[2];
        if(matcher.find()) {
            try {
                result[0] = Integer.parseInt(matcher.group(0));
                result[1] = Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                System.err.println("Invalid input");
                return null;
            }
        }
        return result;
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
     * @param brd
     */
    public static void setBoard(int[][] brd) {
        board = brd;
    }

    public static boolean isMyTurn() {
        return myTurn;
    }

    public static void setTurn(boolean myTurn) {
        Client.myTurn = myTurn;
    }

    public static int getSide() {
        return side;
    }

    public static void setSide(int side) {
        Client.side = side;
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
