package ma.enset.projet.presentation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ma.enset.projet.service.ChatService;

import java.io.IOException;

public class Controllers {
    private static final String USER_ICON_PATH = "/img/user.png";
    private static final String BOT_ICON_PATH = "/img/icon.png";
    private static final String API_KEY = "demo";

    @FXML
    private ListView<Message> messageListView;
    @FXML private TextField inputField;
    @FXML private AnchorPane titleBar;

    private double xOffset = 0;
    private double yOffset = 0;
    private ChatService chatService;

    public void initialize() throws IOException {
        // Initialise le service de chat avec la clé API
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        messageListView.setCellFactory(param -> new MessageListCell());
        messageListView.setMouseTransparent(true);

        // Message de bienvenue
        onChatbotResponse("\uD83C\uDF1F Bienvenue sur le chatbot de l'ENSET! \uD83C\uDF1F\n" +
                "\n" +
                "\uD83D\uDCDA Je suis ici pour vous aider avec toutes vos questions concernant notre école. N'hésitez pas à demander des informations sur les programmes, les événements, les inscriptions, ou tout autre sujet qui vous intéresse. Tapez simplement votre question ci-dessous et je ferai de mon mieux pour vous fournir une réponse rapide et précise. Ensemble, explorons le monde passionnant de l'ENSET!\n" +
                "\n" +
                "\uD83E\uDD16 Posez votre question maintenant et commençons notre voyage d'apprentissage ensemble!");

        chatService = new ChatService(this::onChatbotResponse, API_KEY, "src/main/resources/data/data.txt", "http://localhost:8000/", "data");

    }

@FXML
    private void handleSendButtonAction() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            messageListView.getItems().add(new Message(userInput, "user-message"));
            inputField.clear();

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    chatService.sendMessageToChatbot(userInput);
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    scrollToLastMessage();
                });
            });

            // Gestion des erreurs éventuelles
            task.setOnFailed(e -> {
                // Gérer l'échec de l'envoi du message ici
                System.out.println("Erreur lors de l'envoi du message : " + task.getException());
            });

            // Exécuter la tâche dans un nouveau thread pour éviter de bloquer l'UI
            new Thread(task).start();
        }
    }


    @FXML
    private void handleClear() {
        messageListView.getItems().clear();
    }

    @FXML
    private void handleMinimize(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void onChatbotResponse(String response) {
        Platform.runLater(() -> animateChatbotMessage(response));
    }

    private void animateChatbotMessage(String message) {
        final int[] i = {0};
        Timeline timeline = new Timeline();
        Message animatedMessage = new Message("", "bot-message");
        messageListView.getItems().add(animatedMessage);

        KeyFrame frame = new KeyFrame(Duration.millis(25), event -> {
            if (i[0] < message.length()) {
                animatedMessage.setText(animatedMessage.getText() + message.charAt(i[0]));
                messageListView.refresh();
                i[0]++;
                scrollToLastMessage();
            } else {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void scrollToLastMessage() {
        if (!messageListView.getItems().isEmpty()) {
            messageListView.scrollTo(messageListView.getItems().size() - 1);
        }
    }

    private class MessageListCell extends ListCell<Message> {
        private final ImageView userIcon = createImageView(USER_ICON_PATH);
        private final ImageView botIcon = createImageView(BOT_ICON_PATH);

        private ImageView createImageView(String imagePath) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(26);
            imageView.setFitHeight(26);
            imageView.setPreserveRatio(true);
            return imageView;
        }

        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);
            if (message != null && !empty) {
                setGraphic(createMessageDisplay(message));
            } else {
                setGraphic(null);
                setText(null);
            }
        }

        private HBox createMessageDisplay(Message message) {
            Label label = new Label(message.getText());
            label.getStyleClass().add(message.getType());
            HBox hbox = new HBox(5, "user-message".equals(message.getType()) ? new Node[]{label, userIcon} : new Node[]{botIcon, label});
            hbox.setAlignment("user-message".equals(message.getType()) ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(3, 8, 3, 8));
            return hbox;
        }
    }

    public class Message {
        private String text;
        private String type;

        public Message(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public String getType() {
            return type;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
