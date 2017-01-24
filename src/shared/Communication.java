package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Communication interface, the remote interface which
 * client and server use to communicate
 * Created by samlinz on 24.1.2017.
 */
public interface Communication extends Remote {

    String[] requestOpenRooms() throws RemoteException;

    // join a game room
    boolean joinRoom(int id, String name) throws RemoteException;

    // close connection to server
    void closeConnection(int id) throws RemoteException;

    /**
     * -1 server closed
     * 0 nothing
     * 1 you won
     * 2 the other player won
     * 3 stalemate
     */
    int getSitation(int id) throws RemoteException;

    // true if it's this players turn
    boolean isMyTurn(int id) throws RemoteException;

    // make a move
    void makeMove(int id, int position) throws RemoteException;

    // get the board sitation
    int[][] getBoard(int id) throws RemoteException;
}
