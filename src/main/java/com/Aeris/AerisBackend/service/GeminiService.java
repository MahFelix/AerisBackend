package com.Aeris.AerisBackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendMessage(String prompt, String message) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;

        String requestBody = String.format(
                "{\"contents\":[{\"role\":\"user\",\"parts\":[{\"text\":\"%s\\n\\n%s\"}]}]}",
                prompt, message
        );

        String response = restTemplate.postForObject(url, requestBody, String.class);
        return response; // Processe a resposta conforme necessário
    }
}