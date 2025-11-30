package com.terabia.terabia.models;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.terabia.terabia.dto.UserDto;
import com.terabia.terabia.enums.StatutMessage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages")
@Data @NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private LocalDateTime dateEnvoi;
    private StatutMessage statut;

    @ManyToOne
    @JoinColumn(name = "id_user_emetteur")
    private User emetteur;

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    @JsonIgnoreProperties("messages")
    private Conversation conversation;
}