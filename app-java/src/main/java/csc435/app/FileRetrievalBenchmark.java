package csc435.app;

import java.util.ArrayList;

import javax.naming.directory.SearchResult;

class BenchmarkWorker implements Runnable {
    private ClientProcessingEngine engine;
    private String serverIP;
    private String serverPort;
    private String datasetPath;

    // Constructor to initialize the worker with required details
    public BenchmarkWorker(String serverIP, String serverPort, String datasetPath) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.datasetPath = datasetPath;
    }

    @Override
    public void run() {
        engine = new ClientProcessingEngine();
        engine.connect(serverIP, serverPort);
        engine.indexFolder(datasetPath);
    }

    // Method to search using the first benchmark worker
    public void search() {
        ArrayList<String> terms = new ArrayList<>();
        terms.add("sample");
        SearchResult result = engine.search(terms);
        System.out.println("Search completed in " + result.executionTime + " seconds.");
    }

    // Method to disconnect the engine after operation
    public void disconnect() {
        engine.shutdown();
    }
}

public class FileRetrievalBenchmark {
    public static void main(String[] args) {
        String serverIP = args[0];
        String serverPort = args[1];
        int numberOfClients = Integer.parseInt(args[2]);
        ArrayList<String> datasetPaths = new ArrayList<>();

        // Collect dataset paths from command-line arguments
        for (int i = 3; i < args.length; i++) {
            datasetPaths.add(args[i]);
        }

        long startTime = System.nanoTime();

        // Create and start benchmark worker threads for each client
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            BenchmarkWorker worker = new BenchmarkWorker(serverIP, serverPort, datasetPaths.get(i % datasetPaths.size()));
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Benchmark completed in " + executionTime + " seconds.");

        // Perform search queries on the first client
        BenchmarkWorker firstWorker = new BenchmarkWorker(serverIP, serverPort, datasetPaths.get(0));
        firstWorker.search();

        // Disconnect all client threads
        for (Thread thread : threads) {
            ((BenchmarkWorker) thread).disconnect();
        }
    }
}
