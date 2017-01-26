package client;

/**
 * Service for rendering the game situation in the client side
 * Created by adminpc on 21/1/2017.
 */
public class RenderService {

    public static final char MSGC = '*';

    /**
     * Render the game sitation
     */
    public static void renderBoard() {

    }

    /**
     * Render a message on the screen
     * @param message the message string
     */
    public static void renderMessage(String message) {
        StringBuilder horBar = new StringBuilder();
        for (int i = 0; i < message.length() + 4; i++) {
            horBar.append(MSGC);
        }
        String [] msg = new String[]
                {
                        horBar.toString(),
                        MSGC + " " + message + " " + MMSGC,
                        horBar.toString()
                };
        for(String str : msg) {
            System.out.println(str);
        }
        System.out.println("");
    }
}
