package com.Aeris.AerisBackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public String sendMessage(String prompt, String message) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;

        String requestBody = String.format(
                "{\"contents\":[{\"role\":\"user\",\"parts\":[{\"text\":\"%s\\n\\n%s\"}]}]}",
                prompt, message
        );

        try {
            // Faz a requisição para a API do Gemini
            String response = restTemplate.postForObject(url, requestBody, String.class);

            // Log da resposta bruta
            System.out.println("Resposta bruta do Gemini: " + response);

            // Processa a resposta para extrair o conteúdo
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode contentNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");

            if (contentNode.isMissingNode()) {
                throw new RuntimeException("Resposta inválida da API do Gemini.");
            }

            String contentText = contentNode.asText();
            // Log do conteúdo processado
            System.out.println("Conteúdo processado: " + contentText);

            return contentText; // Retorna o texto da resposta
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a resposta do Gemini: " + e.getMessage(), e);
        }
    }
}