package csc435.app;

import com.sun.security.ntlm.Server;
import io.grpc.ServerBuilder;
import io.grpc.Server;

public class RPCServerWorker implements Runnable {
    private IndexStore store;

    public RPCServerWorker(IndexStore store) {
        this.store = store;
    }

    @Override
    public void run() {
        try {
            Server server = ServerBuilder.forPort(50051)
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
        // No actual shutdown implementation
    }
}
