package client;

/**
 * Service for rendering the game situation in the client side
 * Created by adminpc on 21/1/2017.
 */
public class RenderService {

    public static final char MSGC = '*';

    // player symbols
    public static final char PLR_1 = 'O';
    public static final char PLR_2 = 'X';


    /**
     * Render the game sitation
     */
    public static void renderBoard() {
        int[][] board = Client.getBoard();
        System.out.print("\n");
        for (int i = 0; i < 13; i++) {
            System.out.print('-');
        }
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                char ch = '0';
                int nro = board[y][x];
                switch(nro) {
                    case 1:
                        ch = PLR_1;
                        break;
                    case 2:
                        ch = PLR_2;
                        break;
                }
                System.out.print(" | " + ch);
            }
            System.out.print(" |\n");
        }
        for (int i = 0; i < 13; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
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
                        MSGC + " " + message + " " + MSGC,
                        horBar.toString()
                };
        for(String str : msg) {
            System.out.println(str);
        }
        System.out.println("");
    }
}
