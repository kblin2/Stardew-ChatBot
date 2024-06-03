package springai.aiproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springai.aiproject.services.ChatbotService;
import springai.aiproject.services.CrawlerService;

import java.io.IOException;
import java.util.Map;

@RestController
public class ChatbotController {

    private CrawlerService crawlerService;
    private final ChatbotService openAIService;

    public ChatbotController(ChatbotService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/chat")
    public String generateText(@RequestParam String prompt) throws IOException {
        Map<String, String> allContents = CrawlerService.fetchAllHyperlinkContents("https://stardewvalleywiki.com/Stardew_Valley_Wiki");
        return openAIService.callOpenAI(prompt, allContents);
    }
    // Test that double repo is working
}
