package springai.aiproject.services;

import java.util.HashMap;
import java.util.Map;

public class EmbeddingStorage {
    Map<String, double[]> embeddings;

    public EmbeddingStorage() {
        embeddings = new HashMap<>();
    }

    public void addEmbedding(String text, double[] vector) {
        embeddings.put(text, vector);
    }

    public double[] getEmbedding(String text) {
        return embeddings.get(text);
    }
}