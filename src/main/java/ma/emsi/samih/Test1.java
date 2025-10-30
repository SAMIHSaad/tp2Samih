package ma.emsi.samih;


import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import java.time.Duration;

public class Test1 {

    public static void main(String[] args) {

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-2.5-flash")
                .timeout(Duration.ofSeconds(60))
                .responseFormat(ResponseFormat.JSON)
                .temperature(0.7)
                .build();

        String prompt = "Comment s'appelle le chat de Pierre ?";
        String response = model.chat(prompt);

        System.out.println("Question: " + prompt);
        System.out.println("Réponse: " + response);
    }
}
