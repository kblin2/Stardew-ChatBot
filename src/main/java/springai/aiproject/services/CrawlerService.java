package springai.aiproject.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CrawlerService {

    private static final int MAX_DEPTH = 2;
    private static final String FILE_PATH = "src/main/resources/static/data/content.txt";

    public static Map<String, String> fetchAllHyperlinkContents(String[] startUrl) throws IOException {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        for (String s : startUrl) {
            queue.add(s);
            visited.add(s);
        }

        Map<String, String> allContents = new HashMap<>();

        int depth = 0;

        while (!queue.isEmpty() && depth < MAX_DEPTH) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                String url = queue.poll();
                System.out.println(url);
                try {
                    String content = fetchContent(url);
                    allContents.put(url, content);

                    List<String> links = fetchHyperlinks(url);

                    for (String link : links) {
                        if (!visited.contains(link) && isValidLink(link)) {
                            visited.add(link);
                            queue.add(link);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Failed to fetch content from " + url + ": " + e.getMessage());
                }
            }
            depth++;
        }


        saveContentToFile(allContents);
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

    private static void saveContentToFile(Map<String, String> content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : content.entrySet()) {
                writer.write("URL: " + entry.getKey() + "\n");
                writer.write("Content: " + entry.getValue() + "\n\n");
            }
        }
    }

    public Map<String, String> loadContentFromFile() throws IOException {
        Map<String, String> content = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String url = null;
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("URL: ")) {
                    if (url != null) {
                        content.put(url, sb.toString());
                    }
                    url = line.substring(5);
                    sb.setLength(0);
                } else if (line.startsWith("Content: ")) {
                    sb.append(line.substring(9));
                } else {
                    sb.append(line).append("\n");
                }
            }
            if (url != null) {
                content.put(url, sb.toString());
            }
        }
        return content;
    }
}
