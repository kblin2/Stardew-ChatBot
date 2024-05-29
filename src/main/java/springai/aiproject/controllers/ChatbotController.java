package springai.aiproject.controllers;

import org.springframework.http.ResponseEntity;
import springai.aiproject.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@RequestMapping("/api/openai")
public class ChatbotController {

    private final OpenAIService openAiService;

    public ChatbotController(OpenAIService openAiService) {
        this.openAiService = openAiService;
    }

    @GetMapping("/generate")
    public String generateText(@RequestBody String prompt) {
        return openAiService.callOpenAiApi(prompt);
    }
}
