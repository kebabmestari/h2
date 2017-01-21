package server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminpc on 21/1/2017.
 */
public class GameRoomService {

    private static List<GameRoom> rooms = new ArrayList<>();

    public static void removeRoom(GameRoom room) {
        rooms.remove(room);
    }

    public static boolean roomExists(String name) {
        for(GameRoom room : rooms) {
            if(room.getName().equals(name)) return true;
        }
        return false;
    }

    private static void addRoom(GameRoom room) {
        rooms.add(room);
    }

    public static GameRoom createRoom(String name, int boardSize) {
        if(roomExists(name)) {
            System.err.println("Cannot create room " + name + ". Exists already.");
        }
        GameRoom newRoom = new GameRoom();
        newRoom.setName(name);
        newRoom.setBoardSize(boardSize);
        rooms.add(newRoom);
        System.out.println("Created new game room " + name);
    }

}
