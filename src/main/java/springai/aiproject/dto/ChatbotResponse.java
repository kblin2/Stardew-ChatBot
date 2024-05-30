package springai.aiproject.dto;

import java.util.List;

public class ChatbotResponse {

    private List<Choice> choices;

    public ChatbotResponse(){}

    public ChatbotResponse(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "ChatbotResponse{" +
                "choices=" + choices +
                '}';
    }
}
