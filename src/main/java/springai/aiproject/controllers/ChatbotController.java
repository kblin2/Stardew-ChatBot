package springai.aiproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springai.aiproject.services.ChatbotService;

@RestController
public class ChatbotController {

    private final ChatbotService openAIService;

    public ChatbotController(ChatbotService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/chat")
    public String generateText(@RequestParam String prompt) {
        return openAIService.callOpenAI(prompt);
    }
    // Test that double repo is working
}
