package com.example.twister.Controllers;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.Exceptions.MessageIsEmptyException;
import com.example.twister.Services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/greeting")
    public String greeting(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "main";
    }

    @GetMapping("/home")
    public String home (
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model){
        List<MessageDTO> messages = messageService.getMessagesByFilter(user, filter);
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/messages/{message}/like")
    public String messageLike(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer){
        UriComponents componentsBuilder = messageService.getUriComponentsLikes(currentUser, message, redirectAttributes, referer);
        return "redirect:" + componentsBuilder.getPath();
    }

    @GetMapping("/messages/{message}/saved")
    public String messageSaved(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer){
        UriComponents componentsBuilder = messageService.getUriComponentsSaved(currentUser, message, redirectAttributes, referer);
        return "redirect:" + componentsBuilder.getPath();
    }

    @GetMapping("/messages/{message}/repost")
    public String messageRepost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer){
        UriComponents componentsBuilder = messageService.getUriComponentsReposted(currentUser, message, redirectAttributes, referer);
        return "redirect:" + componentsBuilder.getPath();
    }

    @PostMapping("/home")
    public String add(
            @AuthenticationPrincipal User user,
            Message message,
            @RequestParam("file") Optional<MultipartFile> file,
            Model model){
        try {
            messageService.addMessage(message, user, file);
            addCommonAttributes(model, user);
            return "home";
        } catch (MessageIsEmptyException e){
            model.addAttribute("textError", e.getMessage());
            addCommonAttributes(model, user);
            return "home";
        }
    }

    private void addCommonAttributes(Model model, User user){
        List<MessageDTO> messages = messageService.getMessagesByFilter(user, null);
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
    }
}
