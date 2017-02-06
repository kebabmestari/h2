package client;

import server.ServerCommunicationImpl;
import shared.ClientCommunication;
import shared.ServerCommunication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
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
    private static volatile boolean gameOn = false;
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

        try {
            runGame(cin);
        } catch (Exception e) {
            System.out.println("Received exception, exiting");
            exit(0);
        }

        System.out.println("Exiting..");
        exit(0);
    }

    /**
     * Fetch open room names from the server and display them
     * @param cin cin Scanner
     * @throws RemoteException
     */
    public static void printOpenRooms(Scanner cin) throws RemoteException {
        System.out.println("Getting open rooms..");
        try {
            for(String room: comm.requestOpenRooms()) {
                System.out.println(" * " + room);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompt a room name and attempt to join a room
     * @param cin cin Scanner
     * @throws RemoteException
     */
    public static void joinRoom(Scanner cin) throws RemoteException {
        printOpenRooms(cin);
        while(true) {
            System.out.println("Join room: ");
            String roomName = cin.next();
            if(comm.joinRoom(clientComm, name, roomName)) {
                break;
            } else {
                System.err.println("Failed to join the room");
            };
        }
    }

    /**
     * Main game loop
     * Poll input and react to it
     */
    public static void runGame(Scanner cin) throws Exception {

        // wait until both players have joined and the game is on
        while(!gameOn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // poll user input, exit if q
        String action = "";
        while (!(action = cin.next()).equals("q")) {
            if(isMyTurn()) {
                // parse given input with regex
                int[] coords = parseMove(action);
                if(coords == null || !validMove(coords[0], coords[1])) continue;
                try {
                    // submit move into server, get result
                    final int moveResult = comm.makeMove(name, coords, getSide());
                    if(moveResult == 0) {
                        if(gameOn)
                            System.out.println("Move successful");
                    } else {
                        if(moveResult == 1)
                            System.err.println("Invalid move");
                        else if(moveResult == -1)
                            System.err.println("Game is not on");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if given coordinates are valid aka not oob
     * @param x
     * @param y
     * @return true if valid, false otherwise
     */
    private static  boolean validMove(int x, int y) {
        if(x < 0 || x >= getBoard()[0].length || y < 0 || y > getBoard().length)
            return false;
        return true;
    }

    /**
     * Parse an input string into two coordinates
     * @param str input string
     * @return array of two ints which represent X and Y coordinates
     */
    public static int[] parseMove(String str) {
        // 2x of 1 or two digits with a comma as a separator
        Pattern pattern = Pattern.compile("(\\d{1,2})\\s*,?\\s*(\\d{1,2})");
        Matcher matcher = pattern.matcher(str);
        int[] result = new int[2];
        if(matcher.find()) {
            try {
                // extract the coordinates
                result[0] = Integer.parseInt(matcher.group(1));
                result[1] = Integer.parseInt(matcher.group(2));
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

    /**
     * @return true if it's this client's turn
     */
    public static boolean isMyTurn() {
        return myTurn;
    }

    /**
     * Set the turn flag
     * @param myTurn true if this client's turn
     */
    public static void setTurn(boolean myTurn) {
        Client.myTurn = myTurn;
    }

    /**
     * @return this client's side ID
     */
    public static int getSide() {
        return side;
    }

    /**
     * Set client's side ID
     * @param side 1 or 2
     */
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
