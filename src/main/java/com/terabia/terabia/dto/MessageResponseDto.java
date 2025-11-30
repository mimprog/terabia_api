package com.terabia.terabia.dto;

import com.terabia.terabia.enums.StatutMessage;
import com.terabia.terabia.models.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageResponseDto {
    private Long idMessage;
    private String contenu;
    private LocalDateTime dateEnvoi;
    private StatutMessage statut;
    private UserDto emetteur;
    private Long idConversation;
}