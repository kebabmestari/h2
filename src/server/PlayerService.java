package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by adminpc on 21/1/2017.
 */
public class PlayerService {

    // player repository, maps to the game they are in
    private static Map<Player, GameRoom> players = new HashMap<Player, GameRoom>();

    /**
     * Check if player of given name already exists
     * @param name name of the player
     * @return true if player exists
     */
    public static boolean playerExists(String name) {
        if(getPlayer(name) == null) return false;
        return true;
    }

    /**
     * Return player object by name
     * @param name name of the player
     * @return Player object or null if does not exists
     */
    public static Player getPlayer(String name) {
        for(Player p : players.keySet()) {
            if(p.getName().equals(name)) return p;
        }
        return null;
    }

    /**
     * Remove player from repo, in case of player disconnnecting and such
     */
    public static void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Add a new player
     * @param player
     */
    private static void addPlayer(Player player) {
        players.put(player, null);
    }

    /**
     * Set player room
     * @param player
     * @param room
     */
    private static void setPlayerRoom(Player player, GameRoom room) {
        players.put(player, room);
    }

    public static Player createPlayer(String name) {
        if(playerExists(name)) {
            System.err.println("Trying to create player named " + name + " failed. Exists already.");
            return null;
        }
        Player newPlayer = new Player();
        newPlayer.setName(name);
        addPlayer(newPlayer);
        return newPlayer;
    }
}
