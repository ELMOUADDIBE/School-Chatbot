package ma.enset.projet.Dao;

import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;

import java.time.Duration;

public class VectorBd implements Dao<ChromaEmbeddingStore>{
    private String CHROMA_BASE_URL;
    private String CHROMA_COLLECTION_NAME;


    public VectorBd(String CHROMA_BASE_URL, String CHROMA_COLLECTION_NAME) {
        this.CHROMA_BASE_URL = CHROMA_BASE_URL;
        this.CHROMA_COLLECTION_NAME = CHROMA_COLLECTION_NAME;
    }
    public VectorBd(){

    }

    public String getCHROMA_BASE_URL() {
        return CHROMA_BASE_URL;
    }

    public void setCHROMA_BASE_URL(String CHROMA_BASE_URL) {
        this.CHROMA_BASE_URL = CHROMA_BASE_URL;
    }

    public String getCHROMA_COLLECTION_NAME() {
        return CHROMA_COLLECTION_NAME;
    }

    public void setCHROMA_COLLECTION_NAME(String CHROMA_COLLECTION_NAME) {
        this.CHROMA_COLLECTION_NAME = CHROMA_COLLECTION_NAME;
    }

    @Override
    public ChromaEmbeddingStore getConnection() {
        return ChromaEmbeddingStore.builder()
                .baseUrl(CHROMA_BASE_URL)
                .collectionName(CHROMA_COLLECTION_NAME)
                .timeout(Duration.ofSeconds(6))
                .build();
    }
}
