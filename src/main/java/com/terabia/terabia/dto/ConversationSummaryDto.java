package com.terabia.terabia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversationSummaryDto {
    private Long idConversation;
    private String firstname;
    private String lastname;
}
