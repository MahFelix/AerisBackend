package com.Aeris.AerisBackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.key}")
    private String deepSeekApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeepSeekService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters()
                .add(0, new org.springframework.http.converter.StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String sendMessage(String message, String requestMessage) {
        String url = "https://api.deepseek.com/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + deepSeekApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");
        requestBody.put("stream", false);

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant.");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", message);

        requestBody.put("messages", new Map[]{systemMessage, userMessage});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println("Resposta bruta do DeepSeek: " + response);

            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");

            if (contentNode.isMissingNode()) {
                throw new RuntimeException("Resposta inválida da API do DeepSeek.");
            }

            String contentText = contentNode.asText();
            System.out.println("Conteúdo processado: " + contentText);

            return contentText;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a resposta do DeepSeek: " + e.getMessage(), e);
        }
    }
}
