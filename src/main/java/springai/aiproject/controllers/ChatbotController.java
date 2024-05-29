package springai.aiproject.controllers;

import org.springframework.http.ResponseEntity;
import springai.aiproject.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ChatbotController {

    private final OpenAIService openAIService;

    @Autowired
    public ChatbotController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/message")
    public String getChatbotResponse(@RequestParam String message) {
        try {
            return openAIService.getOpenAIResponse(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to get response from OpenAI.";
        }
    }

    @GetMapping("/test")
    ResponseEntity<String> getGreeting(@RequestParam String greeting) {
        System.out.println(greeting);
        return ResponseEntity.ok(greeting);
    }
}
