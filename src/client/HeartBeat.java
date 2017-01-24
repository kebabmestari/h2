package client;

/**
 * Class for a thread that maintains the
 * heartbeat interval at which the client should
 * poll the server for changes in the game situation
 * Created by adminpc on 21/1/2017.
 */
public class HeartBeat extends Thread {
    // one second
    public static final int INTERVAL = 1000;

    public HeartBeat() {
        super("HEARTBEAT");
        System.out.println("Heartbeat thread created");
    }

    @Override
    public void run() {

        try {
            while (true) {
                // wait for the specified heartbeat time
                Thread.sleep(INTERVAL);
                // call the network service to poll the server
                // TODO: 24.1.2017 POLL
            }
        } catch(InterruptedException e) {
        }
        System.out.println("Closing heartbeat thead");
    }
}
