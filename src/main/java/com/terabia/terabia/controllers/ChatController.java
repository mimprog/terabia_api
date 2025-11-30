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
            @RequestHeader("Authorization") String token,
            @RequestParam Long idConversation,
            @RequestBody @Valid EnvoiMessageDto dto) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer idExpediteur = Integer.parseInt(jwtService.getUserIdFromToken(token));

        Message messageBrut = chatService.envoyerMessage(idExpediteur, idConversation, dto);

        MessageResponseDto response = ChatMapper.toDto(messageBrut);
        return ResponseEntity.ok(response);
    }


    /**
     * RECUPERER MES CONVERSATIONS
     */
    @GetMapping("/conversations")
    public List<ConversationSummaryDto> getConversations(
            @RequestHeader("Authorization") String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Integer userId = Integer.parseInt(jwtService.getUserIdFromToken(token));

        return chatService.getConversationsForUser(userId);
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
            @RequestHeader("Authorization") String token,
            @RequestParam("idDestinataire") Integer idDestinataire
    ) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Get idExpediteur from JWT
        Integer idExpediteur = Integer.parseInt(jwtService.getUserIdFromToken(token));

        // Call the service method
        Map<String, Object> response = chatService.startConversation(idExpediteur, idDestinataire);
        return ResponseEntity.ok(response);
    }






}
