package springai.aiproject.services;

/**
 * @Author: Kenny Lin
 * @Date: 7/2/2024 10:42 AM
 * @Email: kennethlin728@gmail.com
 */
import com.langchain4j.LangChain4j;
import com.langchain4j.model.LangChainModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class LangChainService {

    @PersistenceContext
    private EntityManager entityManager;

    private final LangChainModel langChainModel;

    @Autowired
    public LangChainService(LangChain4j langChain4j) {
        // Initialize the LangChain model
        this.langChainModel = langChain4j.initModel("gpt-3.5-turbo");  // Adjust as necessary
    }

    public String getAnswer(String prompt) {
        try {
            // Fetch possible answers from the database
            String query = "SELECT answer FROM AnswerEntity WHERE prompt = :prompt";
            List<String> results = entityManager.createQuery(query, String.class)
                    .setParameter("prompt", prompt)
                    .getResultList();

            // Check if results are found
            if (results.isEmpty()) {
                return "No answer found for the given prompt.";
            }

            // Process the first result using LangChain4j
            Optional<String> processedAnswer = langChainModel.process(results.get(0));
            return processedAnswer.orElse("Error processing the answer with LangChain4j.");
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching the answer: " + e.getMessage();
        }
    }
}