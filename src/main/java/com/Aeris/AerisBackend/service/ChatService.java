package com.Aeris.AerisBackend.service;

import com.Aeris.AerisBackend.Repository.MessageRepository;
import com.Aeris.AerisBackend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    // Método para enviar mensagens
    public Message sendMessage(String prompt, String message) {
        String response = "Resposta simulada para: " + message;

        Message newMessage = new Message();
        newMessage.setPrompt(prompt);
        newMessage.setMessage(message);
        newMessage.setResponse(response);
        newMessage.setTimestamp(LocalDateTime.now());

        return messageRepository.save(newMessage);
    }

    // Método para buscar mensagens
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}