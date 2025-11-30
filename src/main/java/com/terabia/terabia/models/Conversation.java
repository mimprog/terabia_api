package com.terabia.terabia.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conversations")
@Data @NoArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConversation;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "participation_conversation",
            joinColumns = @JoinColumn(name = "idConversation"),
            inverseJoinColumns = @JoinColumn(name = "idUser")
    )
    @JsonIgnoreProperties("conversations")
    @ToString.Exclude
    @JsonIgnore
    private List<User> participants = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages;
}