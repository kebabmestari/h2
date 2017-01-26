package shared;

/**
 * Enumeration which represents game ending sitation
 * and the related integer code
 * Created by samlinz on 26.1.2017.
 */
public enum GameSituation {
    TERMINATION(-1),
    STALEMATE(0),
    YOU_WON(1),
    YOU_LOST(2);

    private int code;

    GameSituation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
