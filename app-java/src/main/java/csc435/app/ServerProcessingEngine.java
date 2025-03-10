package csc435.app;

import io.grpc.ServerBuilder;
import io.grpc.Server;

public class ServerProcessingEngine {
    private IndexStore store;

    public ServerProcessingEngine(IndexStore store) {
        this.store = store;
    }

    public void initialize(int serverPort) {
        try {
            Server server = ServerBuilder.forPort(serverPort)
                .addService(new FileRetrievalEngineService(store))
                .build();
            server.start();
            System.out.println("Server started...");
            server.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to shutdown the server
    public void shutdown() {
        // No shutdown logic needed for this 
    }
}
