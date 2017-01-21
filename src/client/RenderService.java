package client;

/**
 * Created by adminpc on 21/1/2017.
 */
public class RenderService {

    public static final char MSGC = '*';

    public static void renderBoard() {

    }

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
