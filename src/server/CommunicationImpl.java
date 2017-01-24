package server;

import shared.Communication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samlinz on 24.1.2017.
 */
public class CommunicationImpl extends UnicastRemoteObject implements Communication {
    @Override
    public String[] requestOpenRooms() throws RemoteException {
        final List<GameRoom> rooms = GameRoomService.getRooms();
        List<String> result = new ArrayList<>();
        for(GameRoom gr : rooms) {
            result.add(gr.getName());
        }
        return (String[]) result.toArray();
    }

    @Override
    public boolean joinRoom(int id, String name) throws RemoteException {

    }

    @Override
    public void closeConnection(int id) throws RemoteException {

    }

    @Override
    public int getSitation(int id) throws RemoteException {
        return 0;
    }

    @Override
    public boolean isMyTurn(int id) throws RemoteException {
        return false;
    }

    @Override
    public void makeMove(int id, int position) throws RemoteException {

    }

    @Override
    public int[][] getBoard(int id) throws RemoteException {
        return new int[0][];
    }
}
