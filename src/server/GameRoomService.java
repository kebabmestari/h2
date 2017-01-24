package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by adminpc on 21/1/2017.
 */
public class GameRoomService {

    private static List<GameRoom> rooms = new ArrayList<>();

    public static void removeRoom(GameRoom room) {
        rooms.remove(room);
    }

    public static boolean roomExists(String name) {
        for (GameRoom room : rooms) {
            if (room.getName().equals(name)) return true;
        }
        return false;
    }

    public static void closeRoom(GameRoom gr) {
        gr.
    }

    public static boolean roomHasRoom(GameRoom gr) {
        return gr.getPlayerCount() < GameLogicService.MAXPLAYERS;
    }

    public static GameRoom getRoom(String name) {
        AtomicReference<GameRoom> result = new AtomicReference<>();
        rooms.forEach((gr) -> {
            if (gr.getName().equals(name))
                result.set(gr);
        });
        return result.get();
    }

    public static List<GameRoom> getRooms() {
        return rooms;
    }

    private static void addRoom(GameRoom room) {
        rooms.add(room);
    }

    public static GameRoom createRoom(String name, int boardSize) {
        if (roomExists(name)) {
            System.err.println("Cannot create room " + name + ". Exists already.");
        }
        GameRoom newRoom = new GameRoom();
        newRoom.setName(name);
        newRoom.setBoardSize(boardSize);
        rooms.add(newRoom);
        System.out.println("Created new game room " + name);
        return newRoom;
    }

}
