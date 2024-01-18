package ma.enset.projet.service;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import ma.enset.projet.Dao.VectorBd;

import java.io.IOException;

public class IChat {
    private String API;
    public IChat(){}


    public String getAPI() {
        return API;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public ConversationalRetrievalChain setChat(String path, String URL, String Collection) throws IOException {
        VectorBd vectorBd = new VectorBd(URL,Collection);
        ConversationBuild conversation = new ConversationBuild();

        conversation.setVectorBd(vectorBd);
        conversation.build(path);
        return conversation.chain(API);
    }
}
