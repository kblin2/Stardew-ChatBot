package springai.aiproject.services;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;

public class TextPreprocessor {
    public static String[] tokenizeText(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return tokenizer.tokenize(text);
    }

    public static String[] sentenceDetect(String text) throws IOException {
        try (FileInputStream modelIn = new FileInputStream("src/main/resources/models/word2vec/opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin")) {
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
            return sentenceDetector.sentDetect(text);
        }
    }
}