package server;

import shared.ClientCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static GameRoom getPlayerRoom(Player player) {
        return players.get(player);
    }

    /**
     * Get players in given room
     * @param gr GameRoom object
     * @return ArrayList of Player objects
     */
    public static List<Player> getPlayersInRoom(GameRoom gr) {
        List<Player> result = new ArrayList<>();
        for(Player plr : players.keySet()) {
            if(((GameRoom)players.get(plr)).equals(gr)) {
                result.add(plr);
            }
        }
        return result;
    }

    /**
     * Remove player from repo, in case of player disconnecting and such
     */
    public static void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Add a new player
     * @param player
     */
    private static void addPlayer(Player player, GameRoom room) {
        players.put(player, room);
    }

    /**
     * Set player room
     * @param player
     * @param room
     */
    private static void setPlayerRoom(Player player, GameRoom room) {
        players.put(player, room);
    }

    public static Player createPlayer(String name, ClientCommunication comm, GameRoom gr) {
        if(playerExists(name)) {
            System.err.println("Trying to create player named " + name + " failed. Exists already.");
            return null;
        }
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setComm(comm);
        addPlayer(newPlayer, gr);
        gr.addPlayer(newPlayer);
        return newPlayer;
    }
}
