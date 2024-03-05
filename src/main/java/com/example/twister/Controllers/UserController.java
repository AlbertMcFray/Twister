package com.example.twister.Controllers;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.Interfaces.IMessageRetriever;
import com.example.twister.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User user,
            Model model){
        List<MessageDTO> messages = userService.getUserMessages(user.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        model.addAttribute("IsCurrentUser", true);
        showSubs(user, model);
        return "profile";
    }

    @GetMapping("/user-message/{user}")
    public String userMessages (
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model){
        List<MessageDTO> messages = userService.getUserMessages(user.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        model.addAttribute("IsCurrentUser", currentUser.equals(user));
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        showSubs(user, model);
        return "userMessages";
    }

    @GetMapping("/user-message/reposted/{user}")
    public String userRepostedMessages (
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model){
        Set<MessageDTO> repostedMessages = userService.getRepostedMessages(user.getId());
        model.addAttribute("messages", repostedMessages);
        model.addAttribute("user", user);
        model.addAttribute("IsCurrentUser", currentUser.equals(user));
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        showSubs(user, model);
        return "repostedMessages";
    }

    @GetMapping("/saved")
    public String getSavedMessages(@AuthenticationPrincipal User user, Model model){
        addCommonAttribute(user, model, userService::getSavedUserMessages);
        return "savedMessages";
    }

    @GetMapping("/reposted")
    public String getRepostedMessages(@AuthenticationPrincipal User user, Model model){
        addCommonAttribute(user, model, userService::getRepostedMessages);
        return "repostedMessages";
    }

    private void addCommonAttribute(User user, Model model, IMessageRetriever messageRetriever) {
        Set<MessageDTO> repostedMessages = messageRetriever.getMessage(user.getId());
        model.addAttribute("messages", repostedMessages);
        model.addAttribute("user", user);
        model.addAttribute("IsCurrentUser",true);
        showSubs(user, model);
    }


    private void showSubs(User user, Model model){
        Map<String, Integer> subsData = userService.getSubs(user);
        model.addAttribute("subscribers", subsData.get("subscribers"));
        model.addAttribute("subscriptions", subsData.get("subscriptions"));
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user){
        userService.subscribe(currentUser, user);
        return "redirect:/api/v1/user/user-message/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user){
        userService.unsubscribe(currentUser, user);
        return "redirect:/api/v1/user/user-message/" + user.getId();
    }

    @GetMapping("/{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model){
        model.addAttribute("user", user);
        model.addAttribute("type", type);
        Page<User> usersPage = userService.findUsers(user, type, pageable);
        model.addAttribute("page", usersPage);
        String url = "/api/v1/user" + "/" + type + "/" + user.getId() + "/list";
        model.addAttribute("url", url);
        return "subscriptions";
    }
}
