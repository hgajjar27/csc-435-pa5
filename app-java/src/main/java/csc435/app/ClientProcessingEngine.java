package csc435.app;

import csc435.app.FileRetrievalEngineGrpc.FileRetrievalEngineBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.HashMap;

// class to store the index operation's outcomes
class IndexResult {
    public double executionTime;
    public long totalBytesRead;

    public IndexResult(double executionTime, long totalBytesRead) {
        this.executionTime = executionTime;
        this.totalBytesRead = totalBytesRead;
    }
}

// class to represent the pair of word frequencies and document paths
class DocPathFreqPair {
    public String documentPath;
    public long wordFrequency;

    public DocPathFreqPair(String documentPath, long wordFrequency) {
        this.documentPath = documentPath;
        this.wordFrequency = wordFrequency;
    }
}

// The search results and execution time are stored in a class.
class SearchResult {
    public double executionTime;
    public ArrayList<DocPathFreqPair> documentFrequencies;

    public SearchResult(double executionTime, ArrayList<DocPathFreqPair> documentFrequencies) {
        this.executionTime = executionTime;
        this.documentFrequencies = documentFrequencies;
    }
}

public class ClientProcessingEngine {
    private ManagedChannel channel;
    private FileRetrievalEngineBlockingStub stub;
    private long clientId;

    public ClientProcessingEngine() { }

    // Indexing a folder method (simulated for this purpose)
    public IndexResult indexFolder(String folderPath) {
        IndexResult result = new IndexResult(0.0, 0);

        long startTime = System.nanoTime();
        // Model the indexing process, which would entail reading files in the actual world.
        long totalBytesRead = 0;

        // simulated indexing procedure (real file reading should be used instead)
        totalBytesRead += 5000;

        result.executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        result.totalBytesRead = totalBytesRead;

        return result;
    }

    // How to look for words in the data that is indexed
    public SearchResult search(ArrayList<String> terms) {
        SearchResult result = new SearchResult(0.0, new ArrayList<>());

        long startTime = System.nanoTime();
        // Replace with real search logic to simulate a search.
        ArrayList<DocPathFreqPair> docFreqList = new ArrayList<>();
        docFreqList.add(new DocPathFreqPair("example/path", 5));

        result.executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        result.documentFrequencies = docFreqList;

        return result;
    }

    // Method to get client ID
    public long getInfo() {
        return clientId;
    }

    // Method to connect to the gRPC server
    public void connect(String serverIP, String serverPort) {
        channel = ManagedChannelBuilder.forAddress(serverIP, Integer.parseInt(serverPort))
                .usePlaintext()
                .build();
        stub = FileRetrievalEngineGrpc.newBlockingStub(channel);

        //  For registration, simulate gRPC communication.
        clientId = 12345;  // We would retrieve the client ID from the server in a real-world scenario.

        System.out.println("Connected to server. Client ID: " + clientId);
    }

    // Method to shutdown the connection
    public void shutdown() {
        channel.shutdown();
    }
}
