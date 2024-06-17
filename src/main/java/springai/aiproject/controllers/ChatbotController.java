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
import java.util.List;
import java.util.Map;

@RestController
public class ChatbotController {

    private final CrawlerService crawlerService;
    private final ChatbotService openAIService;

    public ChatbotController(ChatbotService openAIService, CrawlerService crawlerService) {
        this.openAIService = openAIService;
        this.crawlerService  = crawlerService;
    }

    @GetMapping("/chat")
    public String generateText(@RequestParam String prompt) throws IOException {
//        String[] urls = {"https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=%271000+Years+From+Now%27&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Classic+Lamp&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Fruits&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Lightning+Rod&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Pressure+Nozzle&hideredirects=1", "https://stardewvalleywiki.com/mediawiki/index.php?title=Special:AllPages&from=Stone+Walkway+Floor&hideredirects=1"};
//        Map<String, String> allContents = CrawlerService.fetchAllHyperlinkContents(urls);

        // Creating the sentences
        String text = Files.readString(Paths.get("src/main/resources/static/data/combined_output.txt"), StandardCharsets.ISO_8859_1);
//        System.out.println(text);
        String[] sentences = TextPreprocessor.sentenceDetect(text);

        // Generate the word embeddings
        EmbeddingGenerator embeddingGenerator = new EmbeddingGenerator("src/main/resources/models/word2vec/GoogleNews-vectors-negative300.bin");
        EmbeddingStorage storage = new EmbeddingStorage();
        for (String sentence : sentences) {
            double[] vector = embeddingGenerator.getWordVector(sentence);
            storage.addEmbedding(sentence, vector);
        }

        // Process a query
        QueryProcessor queryProcessor = new QueryProcessor(embeddingGenerator);
        System.out.println(prompt);
        String mostSimilarText = SimilaritySearch.findMostSimilar(prompt, storage, queryProcessor);

        // Output the result
        System.out.println("Most similar text: " + mostSimilarText);
        // Extractor not working

        Map<String, String> content = crawlerService.loadContentFromFile();
        return openAIService.callOpenAI(prompt, content);
    }
}
