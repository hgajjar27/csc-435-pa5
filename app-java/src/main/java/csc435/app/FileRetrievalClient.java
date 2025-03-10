package csc435.app;

import csc435.app.client.ClientProcessingEngine;
import csc435.app.client.ClientAppInterface;

public class FileRetrievalClient {
    public static void main(String[] args) {
        // Create a ClientProcessingEngine and associated interface
        ClientProcessingEngine engine = new ClientProcessingEngine();
        ClientAppInterface appInterface = new ClientAppInterface(engine);
        
        // Start reading commands from the user
        appInterface.readCommands();
    }
}
