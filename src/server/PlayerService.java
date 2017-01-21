package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by adminpc on 21/1/2017.
 */
public class PlayerService {

    // player repository
    private static List<Player> players = new ArrayList<>();

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
        for(Player p : players) {
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
     * @param name player name
     */
    private static void addPlayer(Player player) {
        players.add(player);
    }

    public static Player createPlayer(String name) {
        if(playerExists(name)) {
            System.err.println("Trying to create player named " + name + " failed. Exists already.");
            return null;
        }
        Player newPlayer = new Player();
        newPlayer.setName(name);
        addPlayer(newPlayer);
    }
}
