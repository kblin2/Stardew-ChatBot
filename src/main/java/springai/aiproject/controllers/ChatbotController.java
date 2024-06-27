package springai.aiproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springai.aiproject.services.*;
import txtai.Extractor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatbotController {

    private final ChatbotService openAIService;

    public ChatbotController(ChatbotService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/chat")
    public String generateText(@RequestParam String prompt) throws IOException {

        Map<String, String> content = new HashMap<String, String>();
        return openAIService.callOpenAI(prompt, content);
    }
}
