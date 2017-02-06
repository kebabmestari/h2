package shared;

import java.rmi.*;
import java.util.List;

/**
 * ServerCommunication interface, the remote interface which
 * client and server use to communicate
 * Client->Server communication
 * Created by samlinz on 24.1.2017.
 */
public interface ServerCommunication extends Remote {

    List<String> requestOpenRooms() throws RemoteException;

    // join a game room
    boolean joinRoom(ClientCommunication client, String plrname, Stri

            ng roomname) throws RemoteException;

    // close connection to server
    void closeConnection(String plrname) throws RemoteException;

    // make move
    int makeMove(String plrname, int[] coords, int side) throws RemoteException;
}