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
     *
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
        if (players.size() == MAXPLAYERS) {
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
        for (int[] r1 : board) {
            Arrays.fill(r1, 0);
        }
        players.forEach((plr) -> {
            try {
                plr.getComm().passCode(GameSituation.GAME_START);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        activePlayer = players.get(GameLogicService.getStartingPlayer());
        try {
            System.out.println(name + ": Giving active player " + activePlayer.getName() + " side id 1");
            activePlayer.getComm().giveSide(1);
            players.forEach((plr) -> {
                if (plr != activePlayer) {
                    try {
                        System.out.println(name + ": Giving nonactive player " + plr.getName() + " side id 2");
                        plr.getComm().giveSide(2);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        sendBoard();
        sendTurn();
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
     * Get a piece status from coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return 0, 1, 2 representing the status
     */
    public int getPiece(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= board[0].length) || (y >= board.length)) {
            System.err.println("Invalid coordinates " + x + " " + y);
            return -1;
        }
        return board[y][x];
    }

    /**
     * Set a board piece to a number and check the game status
     *
     * @param x
     * @param y
     * @param code
     * @return -1 if invalid state, 0 if successfull, 1 if invalid move
     */
    public int setPiece(int x, int y, int code) {

        System.out.println(name + ": Received move " + x + "," + y + " code " + code);

        if(!(status == GameRoomStatus.INGAME)) return -1;

        if (getPiece(x, y) == 0) {
            if (code >= 1 && code <= 2) {
                board[y][x] = code;
                System.out.println(name + ": move " + code + " at " + x + "," + y);
                changeTurn();
                sendBoard();
                sendTurn();
            } else {
                System.err.println(name + ": Invalid code " + code);
            }
        } else {
            System.err.println(name + ": Invalid move, square not empty");
            return 1;
        }

        // get possible change in game state
        System.out.println(name + ": Checking for winner");
        int state = GameLogicService.getWinner(board);

        if (state == 1 || state == 2){
            System.out.println(name + ": Player " + state + " won");
            try {
                int lost = state == 1 ? 1 : 0;
                players.get(state).getComm().passCode(GameSituation.YOU_WON);
                players.get(lost).getComm().passCode(GameSituation.YOU_LOST);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (state == 3) {
            System.out.println(name + ": Ended in stalemate");
                players.forEach((plr) -> {
                    try {
                        plr.getComm().passCode(GameSituation.STALEMATE);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
        // game ended
        if(state > 0) {
            status = GameRoomStatus.DONE;
        } else {
            System.out.println(name + " no winner, continuing");
        }
        return 0;
    }

    /**
     * Flip the turn
     */
    private void changeTurn() {
        Player nonactive = null;
        for (Player plr : players) {
            if (plr != activePlayer) {
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
