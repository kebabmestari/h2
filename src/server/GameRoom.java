package server;

import shared.GameSituation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
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

    // board size, default 3
    private int boardSize = 3;

    // game board status
    // 0 empty
    // 1 X
    // 2 0
    private int[][] board;

    // constructor
    GameRoom() {
    }

    /**
     * @param boardSize set the game board size
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    /**
     * @return player count
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Add a player to the game and start the game if the
     * room was filled
     * @param player player object
     */
    void addPlayer(Player player) {
        if (players.size() >= MAXPLAYERS) {
            System.err.println("Player " + player.getName() + " tried to connect a full room");
            return;
        }
        System.out.println("Player " + player.getName() + " connected to " + name);
        players.add(player);
        // when enough players have joined, start the game
        if(players.size() == MAXPLAYERS) {
            System.out.println("Room " + name + " filled, initializing new game");
            status = GameRoomStatus.INGAME;
            initGame();
        }
    }

    /**
     * Initialize the game
     */
    private void initGame() {
        // initialize the game board with 0
        board = new int[boardSize][boardSize];
        for(int[] r1 : board) {
            Arrays.fill(r1, 0);
        }
        activePlayer = players.get(GameLogicService.getStartingPlayer());
    }

    /**
     * Send the board information
     */
    private void sendBoard() {
        players.forEach((plr) -> {
            try {
                plr.getComm().passBoardSituation(board);
            } catch (RemoteException e) {
                e.printStackTrace();
                GameRoomService.closeRoom(this, GameSituation.TERMINATION);
                status = GameRoomStatus.DONE;
            }
        });
    }

    /**
     * Send information about the new turn
     */
    private void sendTurn() {
        try {
            activePlayer.getComm().informTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
            GameRoomService.closeRoom(this, GameSituation.TERMINATION);
            status = GameRoomStatus.DONE;
        }
    }

    /**
     * Flip the turn
     */
    private void changeTurn() {
        Player nonactive = null;
        for(Player plr : players) {
            if(plr != activePlayer) {
                nonactive = plr;
                break;
            }
        }
        activePlayer = nonactive;
        System.out.println("Changed turn");
        sendTurn();
    }

    /**
     * @return room name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name room name
     */
    public void setName(String name) {
        this.name = name;
    }
}

enum GameRoomStatus {
    WAITING,
    INGAME,
    DONE
}
