package com.Aeris.AerisBackend.controller;

import com.Aeris.AerisBackend.DTO.ChatRequest;
import com.Aeris.AerisBackend.model.Message;
import com.Aeris.AerisBackend.service.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private DeepSeekService deepseekservice; // Injeta o GeminiService

    @PostMapping
    public Message sendMessage(@RequestBody ChatRequest request) {
        // Validação dos campos
        if (request.getPrompt() == null || request.getMessage() == null) {
            throw new IllegalArgumentException("Os campos 'prompt' e 'message' são obrigatórios.");
        }

        // Chama o DeepSeekService para enviar a mensagem e obter a resposta
        String geminiResponse = deepseekservice.sendMessage(request.getPrompt(), request.getMessage());

        // Cria um objeto Message com a resposta do Gemini
        Message message = new Message();
        message.setPrompt(request.getPrompt());
        message.setMessage(request.getMessage());
        message.setResponse(geminiResponse);

        // Retorna o objeto Message com a resposta do Gemini
        return message;
    }
}