package com.example.twister.Controllers;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    Model model;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProfile() {
        User user = new User();
        user.setId(1L);
        List<MessageDTO> messages = new ArrayList<>();
        when(userService.getUserMessages(user.getId())).thenReturn(messages);

        String viewName = userController.profile(user, model);

        verify(userService, times(1)).getUserMessages(user.getId());
        verify(model, times(1)).addAttribute("messages", messages);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("IsCurrentUser", true);

        assertEquals("profile", viewName);
    }

    @Test
    public void testUserMessages(){
        User currentUser = new User();
        currentUser.setId(1L);
        User user = new User();
        user.setId(2L);
        List<MessageDTO> userMessages = new ArrayList<>();
        when(userService.getUserMessages(currentUser.getId())).thenReturn(userMessages);

        String viewName = userController.userMessages(currentUser, user, model);
        verify(userService, times(1)).getUserMessages(user.getId());
        verify(model, times(1)).addAttribute("messages", userMessages);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("IsCurrentUser", user.getSubscribers().contains(currentUser));

        assertEquals("userMessages", viewName);
    }

    @Test
    public void testUserRepostedMessages(){
        User currentUser = new User();
        currentUser.setId(1L);
        User user = new User();
        user.setId(2L);
        Set<MessageDTO> repostedMessages = new HashSet<>();
        when(userService.getRepostedMessages(user.getId())).thenReturn(repostedMessages);

        String viewName = userController.userRepostedMessages(currentUser, user, model);
        verify(userService, times(1)).getRepostedMessages(user.getId());
        verify(model, times(1)).addAttribute("messages", repostedMessages);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("IsCurrentUser", user.getSubscribers().contains(currentUser));
        verify(model, times(1)).addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));

        assertEquals("repostedMessages", viewName);
    }

    @Test
    public void testGetSavedMessages(){
        User user = new User();
        user.setId(1L);
        Set<MessageDTO> savedMessages = new HashSet<>();
        when(userService.getSavedUserMessages(user.getId())).thenReturn(savedMessages);

        String viewName = userController.getSavedMessages(user, model);
        verify(userService, times(1)).getSavedUserMessages(user.getId());
        verify(model, times(1)).addAttribute("messages", savedMessages);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("IsCurrentUser", true);

        assertEquals("savedMessages", viewName);
    }

    @Test
    public void testGetRepostedMessages(){
        User user = new User();
        user.setId(1L);
        Set<MessageDTO> repostedMessages = new HashSet<>();
        when(userService.getRepostedMessages(user.getId())).thenReturn(repostedMessages);

        String viewName = userController.getRepostedMessages(user, model);

        verify(userService, times(1)).getRepostedMessages(user.getId());
        verify(model, times(1)).addAttribute("messages", repostedMessages);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("IsCurrentUser", true);

        assertEquals("repostedMessages", viewName);
    }

    @Test
    public void testSubscribe() {
        User currentUser = new User();
        currentUser.setId(1L);
        User user = new User();
        user.setId(2L);

        String viewName = userController.subscribe(currentUser, user);

        verify(userService, times(1)).subscribe(currentUser, user);
        assertEquals("redirect:/api/v1/user/user-message/" + user.getId(), viewName);
    }

    @Test
    public void testUnsubscribe() {
        User currentUser = new User();
        currentUser.setId(1L);
        User user = new User();
        user.setId(2L);

        String viewName = userController.unsubscribe(currentUser, user);

        verify(userService, times(1)).unsubscribe(currentUser, user);
        assertEquals("redirect:/api/v1/user/user-message/" + user.getId(), viewName);
    }

    @Test
    public void testUserList() {
        User user = new User();
        user.setId(1L);
        String type = "type";
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        Page<User> usersPage = new PageImpl<>(new ArrayList<>());

        when(userService.findUsers(user, type, pageable)).thenReturn(usersPage);

        String viewName = userController.userList(user, type, pageable, model);

        verify(userService, times(1)).findUsers(user, type, pageable);
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("type", type);
        verify(model, times(1)).addAttribute("page", usersPage);
        verify(model, times(1)).addAttribute("url", "/api/v1/user" + "/" + type + "/" + user.getId() + "/list");

        assertEquals("subscriptions", viewName);
    }
}

