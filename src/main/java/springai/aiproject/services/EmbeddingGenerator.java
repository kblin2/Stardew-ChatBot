package springai.aiproject.services;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import java.io.File;
import java.io.IOException;

public class EmbeddingGenerator {
    private Word2Vec word2Vec;

    public EmbeddingGenerator(String modelPath) throws IOException {
        word2Vec = WordVectorSerializer.readWord2VecModel(new File(modelPath));
    }

    public double[] getWordVector(String word) {
        return word2Vec.getWordVector(word);
    }

    public double[][] getSentenceVectors(String[] sentences) {
        double[][] vectors = new double[sentences.length][];
        for (int i = 0; i < sentences.length; i++) {
            vectors[i] = word2Vec.getWordVector(sentences[i]);
        }
        return vectors;
    }
}
