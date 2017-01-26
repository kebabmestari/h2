package server;

import shared.GameSituation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service for actions related to GameRoom management
 * Created by adminpc on 21/1/2017.
 */
public class GameRoomService {

    // list of rooms on the server
    private static List<GameRoom> rooms = new ArrayList<>();

    /**
     * Remove a room from the list
     * @param room
     */
    public static void removeRoom(GameRoom room) {
        rooms.remove(room);
    }

    /**
     * Check if a room of given name exists
     * @param name name of the room
     * @return true if the room exists, false otherwise
     */
    public static boolean roomExists(String name) {
        for (GameRoom room : rooms) {
            if (room.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Close room
     * @param gr
     */
    public static void closeRoom(GameRoom gr, GameSituation code) {
        System.out.println("Terminating room " + gr.getName() + ".");
        System.out.println("Reson: " + code.getCode());
        PlayerService.getPlayersInRoom(gr).forEach((plr) -> {
            try {
                plr.getComm().passCode(code.getCode());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        removeRoom(gr);
    }

    /**
     * Get if the room has room for more players to join or not
     * @param gr game room ref
     * @return true if there is more room
     */
    public static boolean roomHasRoom(GameRoom gr) {
        return gr.getPlayerCount() < GameLogicService.MAXPLAYERS;
    }

    /**
     * Fetch a GameRoom ref by it's name identifier
     * @param name name of the room, case sensitive
     * @return GameRoom object if found, otherwise null
     */
    public static GameRoom getRoom(String name) {
        AtomicReference<GameRoom> result = new AtomicReference<>();
        rooms.forEach((gr) -> {
            if (gr.getName().equals(name))
                result.set(gr);
        });
        return result.get();
    }

    /**
     * Adds a player to a game
     * @param gr GameRoom ref
     * @param plr Player ref
     */
    public static void addPlayerToRoom(GameRoom gr, Player plr) {
        gr.addPlayer(plr);
    }

    /**
     * @return List of GameRooms active
     */
    public static List<GameRoom> getRooms() {
        return rooms;
    }

    /**
     * Add a new room
     * @param room GameRoom object
     */
    private static void addRoom(GameRoom room) {
        rooms.add(room);
    }

    /**
     * Create a brand new room with the given configuration
     * @param name name of the new room
     * @param boardSize board dimension, same in both directions
     * @return reference to the new object
     */
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
