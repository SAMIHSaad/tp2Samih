package ma.emsi.samih;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;

import java.time.Duration;

public class Test3 {

    public static void main(String[] args) {
        EmbeddingModel embeddingModel = GoogleAiEmbeddingModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-embedding-001")
                .taskType(GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY)
                .outputDimensionality(300)
                .timeout(Duration.ofSeconds(10))
                .build();
        String phrase1 = "Bonjour, comment allez-vous ?";
        String phrase2 = "Salut, quoi de neuf ?";
        Embedding embedding1 = embeddingModel.embed(phrase1).content();
        Embedding embedding2 = embeddingModel.embed(phrase2).content();
        double similarity = dev.langchain4j.store.embedding.CosineSimilarity.between(embedding1, embedding2);
        System.out.printf("Similarité : %.8f%n", similarity);
    }


}
