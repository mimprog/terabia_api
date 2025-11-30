package com.terabia.terabia.chat;

import com.terabia.terabia.dto.EnvoiMessageDto;
import com.terabia.terabia.enums.StatutMessage;
import com.terabia.terabia.models.Conversation;
import com.terabia.terabia.models.Message;
import com.terabia.terabia.models.User;
import com.terabia.terabia.repositories.ConversationRepository;
import com.terabia.terabia.repositories.MessageRepository;
import com.terabia.terabia.repositories.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository utilisateurRepository;

    public ChatService(MessageRepository messageRepository,
                       ConversationRepository conversationRepository,
                       UserRepository utilisateurRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Transactional
    public Message envoyerMessage(Integer idExpediteur, EnvoiMessageDto dto){

        // DEBUG LOG (helpful for future errors)
        System.out.println("=== ENVOI MESSAGE ===");
        System.out.println("idExpediteur = " + idExpediteur);
        System.out.println("idDestinataire = " + dto.getIdDestinataire());
        System.out.println("contenu = '" + dto.getContenu() + "'");

        // 1. Check users
        User expediteur = utilisateurRepository.findById(idExpediteur)
                .orElseThrow(() -> new RuntimeException("Expéditeur introuvable"));

        User destinataire = utilisateurRepository.findById(dto.getIdDestinataire())
                .orElseThrow(() -> new RuntimeException("Destinataire introuvable"));

        // 2. Find or create conversation
        Conversation conversation = conversationRepository
                .findConversationEntreDeuxUsers(idExpediteur, dto.getIdDestinataire())
                .orElseGet(() -> {

                    Conversation newConv = new Conversation();
                    newConv.setParticipants(Arrays.asList(expediteur, destinataire));

                    return conversationRepository.save(newConv);
                });

        // 3. Create message
        Message message = new Message();
        message.setContenu(dto.getContenu() != null ? dto.getContenu() : ""); // prevent null
        message.setDateEnvoi(LocalDateTime.now());
        message.setStatut(StatutMessage.ENVOYE);

        message.setEmetteur(expediteur);
        message.setConversation(conversation);

        return messageRepository.save(message);
    }

    public List<Conversation> getMesConversations(Integer idUser){
        return conversationRepository.findByParticipantsId(idUser);
    }

    public List<Message> getMessagesDeConversation(Long idConversation){
        return messageRepository.findByConversation_IdConversationOrderByDateEnvoiAsc(idConversation);
    }
    public List<User> searchUsers(String query) {
        return utilisateurRepository.searchUsers(query);
    }

    @Transactional
    public Conversation startConversation(Integer idUser1, Integer idUser2) {

        User user1 = utilisateurRepository.findById(idUser1)
                .orElseThrow(() -> new RuntimeException("User1 not found"));

        User user2 = utilisateurRepository.findById(idUser2)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        // check existing conversation
        return conversationRepository
                .findConversationEntreDeuxUsers(idUser1, idUser2)
                .orElseGet(() -> {
                    Conversation newConv = new Conversation();
                    newConv.setParticipants(Arrays.asList(user1, user2));
                    return conversationRepository.save(newConv);
                });
    }
}
