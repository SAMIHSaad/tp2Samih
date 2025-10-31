package ma.emsi.samih;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.time.Duration;
import java.util.Scanner;


public class Test5 {



  public static void main(String[] args) {
    String llmKey = System.getenv("GEMINI_KEY");


    EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
            .apiKey(llmKey)
            .modelName("text-embedding-004")
            .build();

    ChatModel model = GoogleAiGeminiChatModel.builder()
            .apiKey(llmKey)
            .modelName("gemini-2.0-flash")
            .timeout(Duration.ofSeconds(60))
            .temperature(0.3)
            .build();

    String nomDocument = "Machine learning.pdf";
    Document document = FileSystemDocumentLoader.loadDocument(nomDocument);
    EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    DocumentSplitter documentSplitter = DocumentSplitters.recursive(500, 100);

    EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
            .documentSplitter(documentSplitter)
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build();

    ingestor.ingest(document);

    EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .build();


   Assistant assistant = AiServices.builder(Assistant.class)
           .chatModel(model)
           .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
           .contentRetriever(contentRetriever)
           .build();

    conversationAvec(assistant);
  }

  private static void conversationAvec(Assistant assistant) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println("==================================================");
        System.out.print("Posez votre question : ");
        String question = scanner.nextLine();
        if (question.isBlank()) {
          continue;
        }
        System.out.println("==================================================");
        if ("fin".equalsIgnoreCase(question)) {
          break;
        }
        String reponse = assistant.chat(question);
        System.out.println("Assistant : " + reponse);
      }
    }
  }
}
