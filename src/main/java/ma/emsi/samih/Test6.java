
package ma.emsi.samih;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;

public class Test6 {

    public static void main(String[] args) {
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-2.0-flash")
                .logRequests(true)
                .logResponses(true)
                .build();

        AssistantMeteo assistant = AiServices.builder(AssistantMeteo.class)
                .chatModel(model)
                .tools(new MeteoTool())
                .build();

        String question = "Quel temps fait-il à Paris ?";
        System.out.println("Question: " + question);
        String reponse = assistant.chat(question);
        System.out.println("Réponse: " + reponse);
    }
}
