package springai.aiproject.dto;

public class ChatbotRequest {

    private String model;
    private String prompt;

    private int temperature = 1;
    private int max_tokens = 50;
    private int top_p = 1;
    private int frequency_penalty = 0;
    private int presence_penalty = 0;

    public ChatbotRequest() {
    }

    public ChatbotRequest(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(int presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public int getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(int frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public int getTop_p() {
        return top_p;
    }

    public void setTop_p(int top_p) {
        this.top_p = top_p;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "ChatbotRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", temperature=" + temperature +
                ", max_tokens=" + max_tokens +
                ", top_p=" + top_p +
                ", frequency_penalty=" + frequency_penalty +
                ", presence_penalty=" + presence_penalty +
                '}';
    }
}
