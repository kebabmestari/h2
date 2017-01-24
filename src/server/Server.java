import server.GameRoom;
import server.GameRoomService;

import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server software");
        System.out.println("q quit, n new room");
        Scanner cin = new Scanner(System.in);
        String text = "";
        while(!(text = cin.next()).equalsIgnoreCase("q")) {
            if(text.equalsIgnoreCase("n")) {
                newRoom(cin);
                printRooms();
            }
        }
        for(GameRoom gr : GameRoomService.getRooms()) {
            GameRoomService.closeRoom(gr);
        }
        System.out.println("Bye");
    }

    private static void newRoom(Scanner cin) {
        System.out.println("Give name for a new room: ");
        String newRoomName = "unnamed";
        while(!newRoomName.equals("unnamed") && GameRoomService.roomExists(newRoomName)) {
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
        if(boardSize > 3) {
            GameRoomService.createRoom(newRoomName, boardSize);
            System.out.println("Room created");
        } else {
            System.out.println("Invalid board size");
            return;
        }
    }

    private static void printRooms() {
        System.out.println("Rooms open:");
        final List<GameRoom> rooms = GameRoomService.getRooms();
        for(GameRoom gr : rooms) {
            System.out.println(gr.getName() + " Players " + gr.getPlayerCount());
        }
    }
}