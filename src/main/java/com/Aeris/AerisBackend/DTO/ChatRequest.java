package com.Aeris.AerisBackend.DTO;

import lombok.Data;

@Data
public class ChatRequest {
    private String prompt;
    private String message;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}