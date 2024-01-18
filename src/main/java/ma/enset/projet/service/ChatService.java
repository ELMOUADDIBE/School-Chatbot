package ma.enset.projet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import javafx.application.Platform;
import javafx.concurrent.Task;
import ma.enset.projet.service.IChat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class ChatService {
    private HttpClient httpClient;
    private Consumer<String> onResponse;
    private String apiKey;

    private ConversationalRetrievalChain chain;

    // Constructeur modifié pour inclure ConversationalRetrievalChain
    public ChatService(Consumer<String> onResponse, String apiKey, String dataFilePath, String serverUrl, String dataName) throws IOException {
        this.httpClient = HttpClient.newHttpClient();
        this.onResponse = onResponse;
        this.apiKey = apiKey;
        IChat iChat = new IChat();
        iChat.setAPI(apiKey);
        this.chain = iChat.setChat(dataFilePath, serverUrl, dataName);
    }

    public void sendMessageToChatbot(String message) {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return chain.execute(message); // Exécution de la tâche longue
            }
        };

        task.setOnSucceeded(e -> onResponse.accept(task.getValue())); // Mise à jour de l'UI

        new Thread(task).start(); // Démarrage de la tâche dans un nouveau thread
    }
}
