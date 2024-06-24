package springai.aiproject.services;

import java.util.Map;

public class SimilaritySearch {

    /**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vec1 The first vector.
     * @param vec2 The second vector.
     * @return The cosine similarity between the two vectors.
     */
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

    /**
     * Finds the most similar text to the query from the embedding storage.
     *
     * @param query The query text.
     * @param storage The storage containing text embeddings.
     * @param processor The processor to convert query text to its embedding.
     * @return The text from the storage that is most similar to the query.
     */
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
