package ma.emsi.samih;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.time.Duration;

/**
 * Le RAG facile !
 */
public class Test4 {

  // Assistant conversationnel


  public static void main(String[] args) {
    String llmKey = System.getenv("GEMINI_KEY");


    ChatModel model = GoogleAiGeminiChatModel.builder()
            .apiKey(llmKey)
            .modelName("gemini-2.5-flash")
            .timeout(Duration.ofSeconds(60))
            .temperature(0.3)
            .build();

    String nomDocument = "C:\\Users\\saads\\OneDrive\\Documents\\Projets IntelliJ IDEA\\AI\\TPs Casablanca\\tp2Samih\\src\\main\\java\\infos.txt";
    Document document = FileSystemDocumentLoader.loadDocument(nomDocument);
    EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    EmbeddingStoreIngestor.ingest(document, embeddingStore);


    Assistant assistant = AiServices.builder(Assistant.class)
                    .chatModel(model)
                    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                    .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                    .build();

    String question = "Pierre appelle son chat. Qu'est-ce qu'il pourrait dire ?";
    System.out.println("Question : " + question);
    System.out.println("Réponse : " + assistant.chat(question));


  }
}
