package server;

import shared.ClientCommunication;

/**
 * Player class
 * Encloses name, id and various information
 * Provides methods of retrieving the communication stub for the player
 *
 * Created by adminpc on 21/1/2017.
 */
public class Player {

    // player name
    private String name;

    // pipe to player, stub
    private ClientCommunication comm;

    // player id on the field
    private int id;

    Player() {}

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return remote object stub for the client
     */
    public ClientCommunication getComm() {
        return comm;
    }

    /**
     * Set the remote object stub
     * @param comm
     */
    public void setComm(ClientCommunication comm) {
        this.comm = comm;
    }

    /**
     * @return player id number
     */
    public int getId() {
        return id;
    }

    /**
     * Set player id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return player name
     */
    public String getName() {
        return this.name;
    }

}
