package csc435.app;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientAppInterface {
    private ClientProcessingEngine engine;

    // ClientProcessingEngine initialization constructor
    public ClientAppInterface(ClientProcessingEngine engine) {
        this.engine = engine;
    }

    // Technique for reading user instructions
    public void readCommands() {
        Scanner sc = new Scanner(System.in);
        String command;

        // Continue reading instructions until "quit" is typed.
        while (true) {
            System.out.print("> ");
            command = sc.nextLine();

            // Exit if "quit" command is given
            if (command.equals("quit")) {
                break;
            }

            // command to establish a connection with the server
            if (command.startsWith("connect")) {
                String[] parts = command.split(" ");
                if (parts.length == 3) {
                    engine.connect(parts[1], parts[2]);
                }
                continue;
            }

            // To obtain the client ID, use this command.
            if (command.startsWith("get_info")) {
                long clientId = engine.getInfo();
                System.out.println("Client ID: " + clientId);
                continue;
            }

            // An indexing command for a folder
            if (command.startsWith("index")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    String folderPath = parts[1];
                    engine.indexFolder(folderPath);
                }
                continue;
            }

             // The ability to search words
            if (command.startsWith("search"))  {
                String[] parts = command.split(" ");
                if (parts.length > 1) {
                    ArrayList<String> terms = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        terms.add(parts[i]);
                    }
                    engine.search(terms);
                }
                continue;
            }

            System.out.println("Unrecognized command!");
        }

        sc.close();
    }
}
