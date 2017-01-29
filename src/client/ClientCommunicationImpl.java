package client;

import shared.ClientCommunication;
import shared.GameSituation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the remote object in client's end
 * which the server accesses to inform the client of certain actions
 * Created by samlinz on 26.1.2017.
 */
public class ClientCommunicationImpl extends UnicastRemoteObject implements ClientCommunication {

    public static final String MSG_YOURTURN = "Your turn";

    public ClientCommunicationImpl() throws RemoteException {
        System.out.println("Remote client object created");
    }

    @Override
    public void informTurn() throws RemoteException {
        RenderService.renderMessage(MSG_YOURTURN);
        Client.setTurn(true);
    }

    @Override
    public void passBoardSituation(int[][] board) throws RemoteException {
        Client.setBoard(board);
        RenderService.renderBoard();
    }



    @Override
    public void passCode(GameSituation code) throws RemoteException {
        int codeInt = code.getCode();
        switch (codeInt) {
            case -1:
                System.out.println("Game terminated");
                Client.exit(0);
                break;
            case 0:
                System.out.println("Game ends in STALEMATE");
                Client.exit(0);
                break;
            case 1:
                System.out.println("Game ends in YOUR VICTORY");
                Client.exit(0);
                break;
            case 2:
                System.out.println("Game ends in YOUR LOSS");
                Client.exit(0);
                break;
            case 10:
                System.out.println("Game starts!");
                Client.setGameOn(true);
                break;
            default:
                System.err.println("Received invalid game sitation " + code);
        }
    }

    /**
     * Pass the side code to the player
     * @throws RemoteException
     */
    @Override
    public void giveSide(int side) throws RemoteException {
        Client.setSide(side);
    }
}
