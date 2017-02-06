package server;

import shared.GameSituation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

/**
 * Server main class and application entry point
 * For managing the server application and admin input
 */
public class Server {

    /**
     * Server application entry point
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Server software");
        System.out.println("Binding RMI");

        try {
            ServerCommunicationImpl comm = new ServerCommunicationImpl();
            Naming.rebind("comm", comm);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("q quit, n new room, r print rooms");

        Scanner cin = new Scanner(System.in);

        // poll user input
        String text = "";
        while (!(text = cin.next()).equalsIgnoreCase("q")) {
            if (text.equalsIgnoreCase("n")) {
                newRoom(cin);
            }
            if(text.equalsIgnoreCase("r")) {
                printRooms();
            }
        }
        for (GameRoom gr : GameRoomService.getRooms()) {
            System.out.println("Terminating room " + gr.getName());
            GameRoomService.closeRoom(gr, GameSituation.TERMINATION);
        }
        System.out.println("Bye");
        System.exit(0);
    }

    /**
     * Create a new room
     *
     * @param cin Scanner for System input
     */
    private static void newRoom(Scanner cin) {
        System.out.println("Give name for a new room: ");
        String newRoomName = null;
        // poll for room name until an unique name is given
        while (true) {
            newRoomName = cin.next();
            if (!GameRoomService.roomExists(newRoomName)) break;
        }
        int boardSize = 0;
        System.out.println("Board size w x h");
        try {
            boardSize = Integer.parseInt(cin.next());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
            return;
        }
        if (boardSize >= 3) {
            GameRoomService.createRoom(newRoomName, boardSize);
        } else {
            System.out.println("Invalid board size");
            return;
        }
    }

    /**
     * Print the active rooms in the system
     */
    private static void printRooms() {
        System.out.println("\nRooms open:");
        final List<GameRoom> rooms = GameRoomService.getRooms();
        for (GameRoom gr : rooms) {
            System.out.println(gr.getName() + ", players " + gr.getPlayerCount());
        }
        System.out.print("\n");
    }
}