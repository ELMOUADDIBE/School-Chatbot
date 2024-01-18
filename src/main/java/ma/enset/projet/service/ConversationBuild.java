package ma.enset.projet.service;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import ma.enset.projet.Dao.DocumentImp;
import ma.enset.projet.Dao.VectorBd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConversationBuild {

    private final ChatMemory customChatMemory = MessageWindowChatMemory.withMaxMessages(20);
    private DocumentImp document;
    private  VectorBd vectorBd = new VectorBd() ;

    public void setVectorBd(VectorBd vectorBd) {
        this.vectorBd = vectorBd;
    }

    public ConversationBuild() {
    }

    public ChatMemory getCustomChatMemory() {
        return customChatMemory;
    }

    public DocumentImp getDocument() {
        return document;
    }

    public void setDocument(DocumentImp document) {
        this.document = document;
    }

    public VectorBd getVectorBd() {
        return vectorBd;
    }



    public int getMaxResults() {
        return maxResults;
    }

    public Double getMinScore() {
        return minScore;
    }

    private final int maxResults = 4;
    private final Double minScore = 0.7;
    public EmbeddingStoreRetriever retriever(){

        ChromaEmbeddingStore chromaStore = vectorBd.getConnection();
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        return new EmbeddingStoreRetriever(chromaStore, embeddingModel, maxResults, minScore);
    }
    public void build(String path) throws IOException {

        document = new DocumentImp(path);
        String txtFilePath = document.setTxtFilePath(path);
        String txtContent = Files.readString(Path.of(txtFilePath));
        DocumentSplitter lineSplitter = document.SplitDocument();
        Document doc = document.Document(txtContent);

//        EmbeddingStoreIngestor.builder()
//                .documentSplitter(lineSplitter)
//                .embeddingModel(new AllMiniLmL6V2EmbeddingModel())
//                .embeddingStore(vectorBd.getConnection())
//                .build()
//                .ingest(doc);
    }
    public ConversationalRetrievalChain chain(String API){
        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.withApiKey(API))
                .chatMemory(customChatMemory)
                .promptTemplate(document.template())
                .retriever(retriever())
                .build();
    }
}
