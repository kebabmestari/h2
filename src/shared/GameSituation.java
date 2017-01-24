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
    // is the current player the winner
    boolean isVictorious;
    // did the game end in stalemate
    boolean isStalemate;

    public GameSituation(boolean inTurn, boolean isOver, boolean isVictorious, boolean isStalemate) {
        this.inTurn = inTurn;
        this.isOver = isOver;
        this.isVictorious = isVictorious;
        this.isStalemate = isStalemate;
        System.out.println("Created a gamesituation object");
    }

    public GameSituation(boolean inTurn) {
        this(inTurn, false, false, false);
    }
}
