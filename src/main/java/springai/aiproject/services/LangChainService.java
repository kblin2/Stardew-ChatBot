package springai.aiproject.services;

/**
 * @Author: Kenny Lin
 * @Date: 7/2/2024 10:42 AM
 * @Email: kennethlin728@gmail.com
 */
import com.langchain4j.LangChain4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class LangChainService {

    @PersistenceContext
    private EntityManager entityManager;

    private final LangChain4j langChain4j;

    @Autowired
    public LangChainService(LangChain4j langChain4j) {
        this.langChain4j = langChain4j;
    }

    public String getAnswer(String prompt) {
        // Example query, customize based on your schema
        String query = "SELECT answer FROM answers WHERE prompt = :prompt";
        List<String> results = entityManager.createQuery(query, String.class)
                .setParameter("prompt", prompt)
                .getResultList();

        if (results.isEmpty()) {
            return "No answer found for the given prompt.";
        }

        // Use LangChain4j to process the results if necessary
        return langChain4j.process(results.get(0));
    }
}