package springai.aiproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springai.aiproject.services.ChatbotService;
import springai.aiproject.services.CrawlerService;

import java.io.IOException;
import java.util.List;
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
//        String[] urls = {"https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=%271000+Years+From+Now%27&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Classic+Lamp&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Fruits&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Lightning+Rod&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Pressure+Nozzle&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Stone+Walkway+Floor&hideredirects=1"};
//        Map<String, String> allContents = CrawlerService.fetchAllHyperlinkContents(urls);
        Map<String, String> content = CrawlerService.loadContentFromFile();
        return openAIService.callOpenAI(prompt, content);
    }
    // Test that double repo is working
}
