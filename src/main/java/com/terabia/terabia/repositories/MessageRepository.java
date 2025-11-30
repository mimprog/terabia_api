package com.terabia.terabia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.terabia.terabia.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation_IdConversationOrderByDateEnvoiAsc(Long idConversation);
}
