package springai.aiproject.services;

public class QueryProcessor {
    private EmbeddingGenerator embeddingGenerator;

    public QueryProcessor(EmbeddingGenerator embeddingGenerator) {
        this.embeddingGenerator = embeddingGenerator;
    }

    public double[] getQueryEmbedding(String query) {
        return embeddingGenerator.getWordVector(query);
    }
}
