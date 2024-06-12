package springai.aiproject.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.url}")
    private String url;

    private final RestTemplate restTemplate;

    public ChatbotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callOpenAI(String query, Map<String, String> allContents) {
        String combinedContent = allContents.values().stream().collect(Collectors.joining("\n\n"));
        String prompt = "Based on the following information, answer the question: " + query + "\n\n" + combinedContent + "\n\nAnswer:";
//        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        Map<String, Object> message1 = new HashMap<>();
        message1.put("role", "system");
        message1.put("content", "You are Q&A bot. A highly intelligent system that answers\n" +
                "user questions about Stardew Valley based on the information provided by the user with\n" +
                "each question. If the information can not be found in the information\n" +
                "provided by the user you truthfully say \"I don't know\"");

        Map<String, Object> message2 = new HashMap<>();
        message2.put("role", "user");
        message2.put("content", query);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Map[]{message1, message2});
        requestBody.put("max_tokens", 4096);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
