package csc435.app;

public class FileRetrievalServer {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        // Initialize index store and server processing engine
        IndexStore store = new IndexStore();
        ServerProcessingEngine engine = new ServerProcessingEngine(store);
        
        // Start the gRPC server on the specified port
        engine.initialize(serverPort);
    }
}
