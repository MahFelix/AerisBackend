package com.Aeris.AerisBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;
    private String message;
    private String response;

    @Column(updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public void setPrompt(String prompt) {
    }

    public void setMessage(String userMessage) {
    }

    public void setResponse(String response) {
    }
}