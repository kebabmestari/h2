package server;

import shared.ClientCommunication;

/**
 * Created by adminpc on 21/1/2017.
 */
public class Player {

    // player name
    private String name;

    // pipe to player
    ClientCommunication comm;

    Player() {}

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public ClientCommunication getComm() {
        return comm;
    }

    public void setComm(ClientCommunication comm) {
        this.comm = comm;
    }

    /**
     * @return player name
     */
    public String getName() {
        return this.name;
    }

}
