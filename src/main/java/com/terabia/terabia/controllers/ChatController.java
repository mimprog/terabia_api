package com.terabia.terabia.controllers;

import com.terabia.terabia.config.JwtService;
import com.terabia.terabia.dto.ConversationSummaryDto;
import com.terabia.terabia.dto.EnvoiMessageDto;
import com.terabia.terabia.dto.MessageResponseDto;
import com.terabia.terabia.models.Conversation;
import com.terabia.terabia.models.Message;
import com.terabia.terabia.chat.ChatService;
import com.terabia.terabia.models.User;
import com.terabia.terabia.utils.ChatMapper;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    private JwtService jwtService;

    public ChatController(ChatService chatService){
        this.chatService = chatService;
    }


    /**
     * ENVOYER UN MESSAGE
     * React calls: POST /message?idExpediteur=1&idDestinataire=7
     */
    @PostMapping("/message")
    public ResponseEntity<MessageResponseDto> envoyerMessage(
            @RequestParam("idExpediteur") Integer idExpediteur,
            @RequestParam Long idConversation,
            @RequestBody @Valid EnvoiMessageDto dto) {

        Message messageBrut = chatService.envoyerMessage(idExpediteur, idConversation, dto);

        MessageResponseDto response = ChatMapper.toDto(messageBrut);
        return ResponseEntity.ok(response);
    }


    /**
     * RECUPERER MES CONVERSATIONS
     */
    @GetMapping("/conversations")
    public List<ConversationSummaryDto> getConversations(
            @RequestParam("idExpediteur") Integer idUser) {


        return chatService.getConversationsForUser(idUser);
    }
    /**
     * RECUPERER L'HISTORIQUE D'UNE CONVERSATION
     */
    @GetMapping("/messages/{idConversation}")
    public ResponseEntity<List<MessageResponseDto>> getMessages(
            @PathVariable Long idConversation
    ) {

        List<Message> messagesBruts = chatService.getMessagesDeConversation(idConversation);

        List<MessageResponseDto> resultat = messagesBruts.stream()
                .map(ChatMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultat);
    }

    @PostMapping("/conversation/start")
    public ResponseEntity<Map<String, Object>> startConversation(
            @RequestParam("idExpediteur") Integer idExpediteur,
            @RequestParam("idDestinataire") Integer idDestinataire
    )

    {
        // Consider adding validation
        if (idExpediteur == null || idDestinataire == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Both user IDs are required")
            );
        }
        System.out.println("idExpediteur" + idExpediteur + "IDdESTINAire:  " + idDestinataire );

        // Call the service method
        Map<String, Object> response = chatService.startConversation(idExpediteur, idDestinataire);
        return ResponseEntity.ok(response);
    }




}
