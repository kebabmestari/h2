package client;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by adminpc on 21/1/2017.
 */
public class Client {

    public static final String appName = "JTicTacToe";

    private String clientName;

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        RenderService.renderMessage("Welcome to " + appName);
        Scanner cin = new Scanner(System.in);
        System.out.println("Server address:");
        String addr = cin.next();
        System.out.println("Server port:");
        String pport = cin.next();
        int port;
        try {
            port = Integer.parseInt(pport);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port");
            exit(1);
        }

    }

    public static void exit(int code) {
        System.out.println("Exiting..");
        System.exit(code);
    }

    public static void exit() {
        exit(0);
    }
}
