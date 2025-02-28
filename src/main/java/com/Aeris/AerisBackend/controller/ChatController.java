package com.Aeris.AerisBackend.controller;

import com.Aeris.AerisBackend.model.Message;
import com.Aeris.AerisBackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public Message sendMessage(@RequestParam String prompt, @RequestParam String message) {
        return chatService.sendMessage(prompt, message);
    }
}