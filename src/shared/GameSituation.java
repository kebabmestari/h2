package shared;

/**
 * Enumeration which represents game state altering sitation
 * and the related integer code
 * Created by samlinz on 26.1.2017.
 */
public enum GameSituation {
    TERMINATION(-1),
    STALEMATE(0),
    YOU_WON(1),
    YOU_LOST(2),
    GAME_START(10);

    // the integer code
    private int code;

    // constructor
    GameSituation(int code) {
        this.code = code;
    }

    /**
     * @return the integer code
     */
    public int getCode() {
        return code;
    }
}
