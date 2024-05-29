package springai.aiproject.services;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OpenAIService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/completions";
    private static final Logger logger = Logger.getLogger(OpenAIService.class.getName());

    public String getOpenAIResponse(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        System.out.println(apiKey);
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IOException("API key is not set");
        }

        logger.log(Level.INFO, "Using API Key: " + apiKey);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("prompt", message);
        jsonBody.put("max_tokens", 150);
        jsonBody.put("model", "text-davinci-003");

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(OPENAI_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        logger.log(Level.INFO, "Request Headers: " + request.headers());

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.log(Level.SEVERE, "Unexpected code: " + response);
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            logger.log(Level.INFO, "Response: " + responseBody);

            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error getting response from OpenAI: " + e.getMessage(), e);
            throw e;
        }
    }
}
