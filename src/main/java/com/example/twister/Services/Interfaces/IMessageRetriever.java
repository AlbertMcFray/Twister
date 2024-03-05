package com.example.twister.Services.Interfaces;

import com.example.twister.Domain.DTO.MessageDTO;

import java.util.Set;

//Func Interface
public interface IMessageRetriever {
    Set<MessageDTO> getMessage(Long userId);
}
