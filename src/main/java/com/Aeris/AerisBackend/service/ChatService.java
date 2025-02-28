package com.Aeris.AerisBackend.service;

import com.Aeris.AerisBackend.Repository.MessageRepository;
import com.Aeris.AerisBackend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private GeminiService geminiService;

    public Message sendMessage(String prompt, String userMessage) {
        String response = geminiService.sendMessage(prompt, userMessage);

        Message message = new Message();
        message.setPrompt(prompt);
        message.setMessage(userMessage);
        message.setResponse(response);

        return messageRepository.save(message);
    }
}