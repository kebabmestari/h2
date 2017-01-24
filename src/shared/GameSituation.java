package shared;

import java.io.Serializable;

/**
 * Created by adminpc on 24/1/2017.
 */
public class GameSituation implements Serializable {

    // is the current player in turn
    boolean inTurn;
    // is the game over
    boolean isOver;

    /**
     * Ending sitation
     * 0 invalid ending, the other player disconnected etc
     * 1 you won
     * 2 the other player won
     * 3 stalemate
     */
    int endSituation;

    public GameSituation(boolean inTurn, boolean isOver, int sitation) {
        this.inTurn = inTurn;
        this.isOver = isOver;
        this.endSituation = situation;
        System.out.println("Created a gamesituation object");
    }

    public GameSituation(boolean inTurn) {
        this(inTurn, false, false, false);
    }
}
