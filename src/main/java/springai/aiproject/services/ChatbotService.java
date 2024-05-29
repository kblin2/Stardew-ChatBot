package springai.aiproject.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatbotService {

    private final Map<String, String> responses;

    public ChatbotService() {
        responses = new HashMap<>();
        responses.put("hello", "Hi there! How can I help you today?");
        responses.put("how are you", "I'm a chatbot, so I'm always good!");
        responses.put("bye", "Goodbye! Have a great day!");
    }

    public String getResponse(String message) {
        return responses.getOrDefault(message.toLowerCase(), "Sorry, I don't understand that.");
    }
}
