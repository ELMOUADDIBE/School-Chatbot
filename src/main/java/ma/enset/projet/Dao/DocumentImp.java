package ma.enset.projet.Dao;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter;
import dev.langchain4j.model.input.PromptTemplate;

public class DocumentImp {
    private  String txtFilePath;
    private final int maxSegmentSizeInChars = 350;
    private final int maxOverlapSizeInChars = 0;

    public DocumentImp(String txtFilePath) {
        this.txtFilePath = txtFilePath;
    }

    public String getTxtFilePath() {
        return txtFilePath;
    }

    public String setTxtFilePath(String txtFilePath) {
        this.txtFilePath = txtFilePath;
        return txtFilePath;
    }

    public int getMaxSegmentSizeInChars() {
        return maxSegmentSizeInChars;
    }

    public int getMaxOverlapSizeInChars() {
        return maxOverlapSizeInChars;
    }
    public DocumentSplitter SplitDocument(){
        return new DocumentByLineSplitter(maxSegmentSizeInChars, maxOverlapSizeInChars);

    }
    public dev.langchain4j.data.document.Document Document(String s){
        return dev.langchain4j.data.document.Document.from(s);
    }
    public PromptTemplate template(){
        return PromptTemplate.from(
                "En tant qu'assistant virtuel spécialisé dans les informations sur l'ENSET Mohammedia, une école d'ingénierie au Maroc, je suis ici pour aider. " +
                        "Je dispose d'informations actualisées sur divers sujets liés à l'école, y compris mais sans s'y limiter, les programmes académiques, les annonces importantes, les procédures administratives, et les événements de l'école. " +
                        "Je peux fournir des détails sur les formations disponibles, comme les programmes de Master, le cycle doctoral, et le diplôme d'Ingénierie, couvrant des domaines variés tels que la programmation orientée objet en Java, l'UML, la gestion de projets, l'économie, ainsi que l'électrique et la mécanique. " +
                        "Pour toute information officielle supplémentaire, je recommande de consulter le site web de l'ENSET à www.enset-media.ac.ma.\n\n" +
                        "Votre question: {{question}}\n\n" +
                        "Voici une réponse précise et adaptée, basée sur les informations les plus récentes et le contexte spécifique à l'ENSET Mohammedia :\n{{information}}\n\n");
    }
}
