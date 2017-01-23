package server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminpc on 21/1/2017.
 */
public class GameRoom {

    public static final int MAXPLAYERS = 2;

    // players in room
    private List<Player> players = new ArrayList<>();
    // initially waiting for players to join
    private GameRoomStatus status = GameRoomStatus.WAITING;

    // ref to player in turn
    private Player activePlayer = null;

    // room name
    private String name;

    private int boardSize = 3;

    GameRoom() {
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getPlayerCount() {
        return players.size();
    }

    void addPlayer(Player player) {
        if (players.size() >= MAXPLAYERS) {
            System.err.println("Player " + player.getName() + " tried to connect a full room");
            return;
        }
        players.add(player);
        // when enough players have joined, start the game
        if(players.size() == MAXPLAYERS) {
            status = GameRoomStatus.INGAME;
        }
    }

    /**
     * Initialize the game
     */
    private void initGame() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

enum GameRoomStatus {
    WAITING,
    INGAME,
    DONE
}