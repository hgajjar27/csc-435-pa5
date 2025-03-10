package csc435.app;

import java.util.ArrayList;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Empty;

// gRPC Service Implementation
public class FileRetrievalEngineService extends FileRetrievalEngineGrpc.FileRetrievalEngineImplBase {
    private IndexStore store;

    // Constructor to initialize the index store
    public FileRetrievalEngineService(IndexStore store) {
        this.store = store;
    }

    // Method to register the client and return a client ID
    public void register(Empty request, StreamObserver<RegisterRep> responseObserver) {
        long clientId = store.putDocument("new_client");
        responseObserver.onNext(RegisterRep.newBuilder().setClientId(clientId).build());
        responseObserver.onCompleted();
    }

    // Method to process the index request
    public void computeIndex(IndexReq request, StreamObserver<IndexRep> responseObserver) {
        store.updateIndex(request.getDocumentNumber(), request.getWordFrequenciesMap());
        responseObserver.onNext(IndexRep.newBuilder().setStatus("OK").build());
        responseObserver.onCompleted();
    }

    // Method to process search request
    public void computeSearch(SearchReq request, StreamObserver<SearchRep> responseObserver) {
        ArrayList<DocFreqPair> results = store.lookupIndex(request.getSearchTerm());
        SearchRep.Builder builder = SearchRep.newBuilder();
        for (DocFreqPair pair : results) {
            builder.addDocuments(DocFreqPair.newBuilder()
                    .setDocumentNumber(pair.documentNumber)
                    .setWordFrequency(pair.wordFrequency).build());
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
