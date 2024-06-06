package springai.aiproject.services;

import java.util.Map;

public class SimilaritySearch {
    public static double cosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normA += Math.pow(vec1[i], 2);
            normB += Math.pow(vec2[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static String findMostSimilar(String query, EmbeddingStorage storage, QueryProcessor processor) {
        double[] queryVec = processor.getQueryEmbedding(query);
        String mostSimilarText = null;
        double highestSimilarity = -1;

        for (Map.Entry<String, double[]> entry : storage.embeddings.entrySet()) {
            double similarity = cosineSimilarity(queryVec, entry.getValue());
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                mostSimilarText = entry.getKey();
            }
        }

        return mostSimilarText;
    }
}
