package springai.aiproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AiprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiprojectApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@PostMapping("/generatePrompt")
	public String generatePrompt(@RequestBody String prompt) {
		String apiUrl = "https://api.openai.com/v1/completions";

		RestTemplate restTemplate = new RestTemplate();
		String requestBody = "{\"model\":\"gpt-3.5-turbo\", \"prompt\":\"" + prompt + "\", \"max_tokens\":50}";

		try {
			String response = restTemplate.postForObject(apiUrl, requestBody, String.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error occurred: " + e.getMessage();
		}
	}
}
