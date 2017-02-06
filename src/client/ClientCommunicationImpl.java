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

    /**
     * Inform the client of its turn
     * @throws RemoteException
     */
    @Override
    public void informTurn() throws RemoteException {
        RenderService.renderMessage(MSG_YOURTURN);
        Client.setTurn(true);
    }

    /**
     * Pass the pawn situation on board
     * @param board two dimensional int array representing the board
     * @throws RemoteException
     */
    @Override
    public void passBoardSituation(int[][] board) throws RemoteException {
        Client.setBoard(board);
        RenderService.renderBoard();
    }

    /**
     * Pass a code representing a sitation in the game
     * @param code GameSitation enum object
     * @throws RemoteException
     */
    @Override
    public void passCode(GameSituation code) throws RemoteException {
        int codeInt = code.getCode();
        switch (codeInt) {
            case -1:
                System.out.println("Game terminated");
                System.out.println("q to exit");
                Client.setGameOn(false);
                break;
            case 0:
                System.out.println("Game ends in STALEMATE");
                System.out.println("q to exit");
                Client.setGameOn(false);
                break;
            case 1:
                System.out.println("Game ends in YOUR VICTORY");
                System.out.println("q to exit");
                Client.setGameOn(false);
                break;
            case 2:
                System.out.println("Game ends in YOUR LOSS");
                System.out.println("q to exit");
                Client.setGameOn(false);
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
        System.out.println("Server gave us side: " + side);
        Client.setSide(side);
    }
}
