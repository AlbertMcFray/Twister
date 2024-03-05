package com.example.twister.Domain.Entity.Util;

import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.DTO.MessageDTO;

public abstract class MessageUtil {
    public static String getAuthorName(User author){
        return author != null ? author.getUsername() : "<none>";
    }
    public static MessageDTO convertToDTO(User currentUser, Message message) {
        boolean isLiked = message.getLikes().contains(currentUser);
        boolean isSaved = message.getSaves().contains(currentUser);
        boolean isReposted = message.getReposts().contains(currentUser);
        return MessageDTO.builder()
                .id(message.getId())
                .text(message.getText())
                .tag(message.getTag())
                .author(message.getAuthor())
                .filename(message.getFilename())
                .likes((long) message.getLikes().size())
                .isLiked(isLiked)
                .saves((long) message.getSaves().size())
                .isSaved(isSaved)
                .reposts((long) message.getReposts().size())
                .isReposted(isReposted)
                .posted(message.getPosted())
                .build();
    }
}
