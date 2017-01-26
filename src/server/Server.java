package server;

import shared.GameSituation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

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

        System.out.println("q quit, n new room");
        Scanner cin = new Scanner(System.in);
        String text = "";
        while (!(text = cin.next()).equalsIgnoreCase("q")) {
            if (text.equalsIgnoreCase("n")) {
                newRoom(cin);
                printRooms();
            }
        }
        for (GameRoom gr : GameRoomService.getRooms()) {
            System.out.println("Terminating room " + gr.getName());
            GameRoomService.closeRoom(gr, GameSituation.TERMINATION);
        }
        System.out.println("Bye");
    }

    /**
     * Create a new room
     *
     * @param cin Scanner for System input
     */
    private static void newRoom(Scanner cin) {
        System.out.println("Give name for a new room: ");
        String newRoomName = "unnamed";
        // poll for room name until an unique name is given
        while (!newRoomName.equals("unnamed") || GameRoomService.roomExists(newRoomName)) {
            newRoomName = cin.next();
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
            System.out.println("Room created");
        } else {
            System.out.println("Invalid board size");
            return;
        }
    }

    /**
     * Print the active rooms in the system
     */
    private static void printRooms() {
        System.out.println("Rooms open:");
        final List<GameRoom> rooms = GameRoomService.getRooms();
        for (GameRoom gr : rooms) {
            System.out.println(gr.getName() + " Players " + gr.getPlayerCount());
        }
    }
}