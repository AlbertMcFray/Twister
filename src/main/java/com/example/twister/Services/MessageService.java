package com.example.twister.Services;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Entity.Util.MessageUtil;
import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Repositories.MessageRepository;
import com.example.twister.Domain.Entity.Message;
import com.example.twister.Services.Exceptions.MessageIsEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    @Value("${upload.path}")
    private String uploadPath;

    public List<MessageDTO> getMessagesByFilter(User currentUser, String filter) {
        List<Message> messages;
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        return messages.stream()
                .map(message -> MessageUtil.convertToDTO(currentUser, message))
                .collect(Collectors.toList());
    }

    public List<Message> getMessages() {
        List<Message> messages = messageRepository.findAll().stream().collect(Collectors.toList());
        return messages;
    }

    public void addMessage(Message message, User user, Optional<MultipartFile> fileOpt){
        message.setAuthor(user);
        validateMessageText(message);
        processFile(message, fileOpt);
        messageRepository.save(message);
    }

    public void processFile(Message message, Optional<MultipartFile> fileOpt) {
        if(fileOpt.isPresent()) {
            MultipartFile file = fileOpt.get();
            if (!file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                try {
                    file.transferTo(new File(uploadPath + "/" + resultFilename));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                message.setFilename(resultFilename);
            }
        }
    }

    private void validateMessageText(Message message) {
        if (message.getText() == null || message.getText().trim().isEmpty()){
            throw new MessageIsEmptyException("Message is empty");
        }
    }

    public UriComponents getUriComponentsLikes(User currentUser, Message message, RedirectAttributes redirectAttributes, String referer) {
        return getUriComponents(currentUser, message.getLikes(), redirectAttributes, referer);
    }

    public UriComponents getUriComponentsSaved(User currentUser, Message message, RedirectAttributes redirectAttributes, String referer) {
        return getUriComponents(currentUser, message.getSaves(), redirectAttributes, referer);
    }

    public UriComponents getUriComponentsReposted(User currentUser, Message message, RedirectAttributes redirectAttributes, String referer) {
        return getUriComponents(currentUser, message.getReposts(), redirectAttributes, referer);
    }


    public UriComponents getUriComponents(User currentUser, Set<User> userset, RedirectAttributes redirectAttributes, String referer) {
        if(userset.contains(currentUser)){
            userset.remove(currentUser);
        } else {
            userset.add(currentUser);
        }
        UriComponents componentsBuilder = UriComponentsBuilder.fromHttpUrl(referer).build(false);
        componentsBuilder
                .getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return componentsBuilder;
    }
}
