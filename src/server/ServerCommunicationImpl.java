package server;

import client.Client;
import shared.ClientCommunication;
import shared.ServerCommunication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for the server remote object which the
 * client end uses to pass information to the server
 * Created by samlinz on 24.1.2017.
 */
public class ServerCommunicationImpl extends UnicastRemoteObject implements ServerCommunication {

    // constructor
    public ServerCommunicationImpl() throws RemoteException {
        System.out.println("Remote server object created");
    }

    /**
     * Return list of open rooms
     *
     * @return String array of names of the open game rooms
     * @throws RemoteException
     */
    @Override
    public List<String> requestOpenRooms() throws RemoteException {
        final List<GameRoom> rooms = GameRoomService.getRooms();
        List<String> result = new ArrayList<>();
        for (GameRoom gr : rooms) {
            result.add(gr.getName());
        }
        return result;
    }

    /**
     * Join a room as a client and pass reference to itself and various information
     * so the server can contact the client
     *
     * @param client   client remote object reference
     * @param plrname  player name for identification
     * @param roomname name of the room the player wishes to join
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean joinRoom(ClientCommunication client, String plrname, String roomname) throws RemoteException {
        GameRoom gr = GameRoomService.getRoom(roomname);
        if (gr == null) return false;
        if (!GameRoomService.roomHasRoom(gr)) return false;
        PlayerService.createPlayer(plrname, client, gr);
        System.out.println(PlayerService.getPlayer(plrname).getName() + " created");
        return true;
    }

    /**
     * Close connection to the server
     * @param plrname player name
     * @throws RemoteException
     */
    @Override
    public void closeConnection(String plrname) throws RemoteException {

    }

    /**
     * Make a move on the board
     * @param plrname player name
     * @param coords coordinates as two size array [x,y]
     * @param side 1 or 2 which represents the player 'id' which again represents either X or O
     * @return -1 for invalid state, 0 for no error or 1 for invalid move
     * @throws RemoteException
     */
    @Override
    public int makeMove(String plrname, int[] coords, int side) throws RemoteException {
        Player plr = PlayerService.getPlayer(plrname);
        System.out.println(plr.getName());
        GameRoom gr = PlayerService.getPlayerRoom(plr);
        System.out.println(gr.getName());
        return gr.setPiece(coords[0], coords[1], side);
    }
}
