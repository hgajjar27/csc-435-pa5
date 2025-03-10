package csc435.app;

import java.util.Scanner;

public class ServerAppInterface {
    private ServerProcessingEngine engine;

    public ServerAppInterface(ServerProcessingEngine engine) {
        this.engine = engine;
    }

    public void readCommands() {
        Scanner sc = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");
            command = sc.nextLine();

            if (command.equals("quit")) {
                break;
            }

            System.out.println("Unrecognized command!");
        }

        sc.close();
    }
}
