package csc435.app;

import java.util.ArrayList;
import java.util.HashMap;

class DocFreqPair {
    public long documentNumber;
    public long wordFrequency;

    public DocFreqPair(long documentNumber, long wordFrequency) {
        this.documentNumber = documentNumber;
        this.wordFrequency = wordFrequency;
    }
}

public class IndexStore {
    private HashMap<String, ArrayList<DocFreqPair>> termInvertedIndex;
    private HashMap<Long, String> documentMap;

    public IndexStore() {
        termInvertedIndex = new HashMap<>();
        documentMap = new HashMap<>();
    }

    // Method to put a document into the store
    public long putDocument(String documentPath) {
        long documentNumber = documentMap.size() + 1;
        documentMap.put(documentNumber, documentPath);
        return documentNumber;
    }

    // Method to retrieve a document by its ID
    public String getDocument(long documentNumber) {
        return documentMap.get(documentNumber);
    }

    // Method to update the inverted index with word frequencies from a document
    public void updateIndex(long documentNumber, HashMap<String, Long> wordFrequencies) {
        for (String term : wordFrequencies.keySet()) {
            termInvertedIndex.putIfAbsent(term, new ArrayList<>());
            termInvertedIndex.get(term).add(new DocFreqPair(documentNumber, wordFrequencies.get(term)));
        }
    }

    // Method to perform a lookup on the inverted index for a given term
    public ArrayList<DocFreqPair> lookupIndex(String term) {
        return termInvertedIndex.getOrDefault(term, new ArrayList<>());
    }
}
