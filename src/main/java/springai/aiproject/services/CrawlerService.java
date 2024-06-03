package springai.aiproject.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CrawlerService {

    private static final int MAX_DEPTH = 3;

    public static Map<String, String> fetchAllHyperlinkContents(String startUrl) throws IOException {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startUrl);
        visited.add(startUrl);

        Map<String, String> allContents = new HashMap<>();

        int depth = 0;

        while (!queue.isEmpty() && depth < MAX_DEPTH) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String url = queue.poll();
                System.out.println(url);
                String content = fetchContent(url);
                allContents.put(url, content);

                List<String> links = fetchHyperlinks(url);

                for (String link : links) {
                    if (!visited.contains(link) && isValidLink(link)) {
                        visited.add(link);
                        queue.add(link);
                    }
                }
            }
            depth++;
        }

        return allContents;
    }

    private static List<String> fetchHyperlinks(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        List<String> hyperlinks = new ArrayList<>();
        for (Element link : links) {
            String href = link.attr("abs:href");
            hyperlinks.add(href);
        }
        return hyperlinks;
    }

    private static String fetchContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.body().text();
    }

    private static boolean isValidLink(String link) {
        return link.startsWith("http") && !link.contains("#");
    }
}
