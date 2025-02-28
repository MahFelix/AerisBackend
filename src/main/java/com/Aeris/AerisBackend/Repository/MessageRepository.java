package com.Aeris.AerisBackend.Repository;

import com.Aeris.AerisBackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}