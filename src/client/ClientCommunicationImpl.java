package client;

import shared.ClientCommunication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the remote object in client's end
 * which the server accesses to inform the client of certain actions
 * Created by samlinz on 26.1.2017.
 */
public class ClientCommunicationImpl extends UnicastRemoteObject implements ClientCommunication {

    public static final String MSG_YOURTURN = "Your turn";

    @Override
    public void informTurn() throws RemoteException {
        RenderService.renderMessage(MSG_YOURTURN);
    }

    @Override
    public void passBoardSituation(int[][] board) throws RemoteException {
        Client.setBoard(board);
        RenderService.renderBoard();
    }

    @Override
    public void passGameEnd(int code) throws RemoteException {

    }
}
