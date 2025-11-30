package com.terabia.terabia.utils;

import com.terabia.terabia.dto.MessageResponseDto;
import com.terabia.terabia.dto.UserDto;
import com.terabia.terabia.models.Message;
import com.terabia.terabia.models.User;

public class ChatMapper {

    public static MessageResponseDto toDto(Message message) {
        if (message == null) return null;

        MessageResponseDto dto = new MessageResponseDto();
        dto.setIdMessage(message.getIdMessage());
        dto.setContenu(message.getContenu());
        dto.setDateEnvoi(message.getDateEnvoi());
        dto.setStatut(message.getStatut());
        dto.setIdConversation(message.getConversation().getIdConversation());

        // Mapping manuel de l'émetteur
        User user = message.getEmetteur();
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());



        dto.setEmetteur(userDto);

        return dto;
    }
}