package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ServerCommunication interface, the remote interface which
 * client and server use to communicate
 * Created by samlinz on 24.1.2017.
 */
public interface ServerCommunication extends Remote {

    String[] requestOpenRooms() throws RemoteException;

    // join a game room
    boolean joinRoom(ClientCommunication client, String plrname, String roomname) throws RemoteException;

    // close connection to server
    void closeConnection(String plrname) throws RemoteException;
}
