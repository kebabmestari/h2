package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by samlinz on 25.1.2017.
 */
public interface ClientCommunication extends Remote {
    // server informs the client of it's turn
    void informTurn() throws RemoteException;

    // pass game situation
    void passBoardSituation(int[][] board) throws RemoteException;

    // pass information about game situation
    // -1 invalid state, the other player disconnected etc
    // 0 stalemate
    // 1 you won
    // 2 the other player won
    // 10 the game started
    void passCode(int code) throws RemoteException;
}
