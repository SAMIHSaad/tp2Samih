package ma.emsi.samih;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.Prompt;

import java.util.HashMap;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        PromptTemplate promptTemplate = PromptTemplate.from("Traduis le texte suivant en anglais : {{text}}");

        Map<String, Object> variables = new HashMap<>();
        variables.put("text", "Bonjour, comment allez-vous ?");

        Prompt prompt = promptTemplate.apply(variables);

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-2.5-flash")
                .build();

        String response = model.chat(prompt.text());

        System.out.println(response);
    }
}
