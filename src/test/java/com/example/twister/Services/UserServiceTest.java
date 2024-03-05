package com.example.twister.Services;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Repositories.UserRepository;
import com.example.twister.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById(){
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testGetUserList(){
        List<User> userList = Arrays.asList(new User(), new User());

        when(userRepository.findAll()).thenReturn(userList);
        List<User> resultList = userService.getUserList();

        assertNotNull(resultList);
        assertEquals(userList.size(), resultList.size());
        assertEquals(userList, resultList);
    }

    @Test
    public void testGetUserMessages(){
        User user = new User();
        user.setId(1L);
        Message message1 = new Message();
        Message message2 = new Message();
        user.setMessages(Arrays.asList(message1, message2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<MessageDTO> result = userService.getUserMessages(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetSavedUserMessages(){
        User user = new User();
        user.setId(1L);
        Message message1 = new Message();
        Message message2 = new Message();
        message1.setText("testText1");
        message2.setText("testText2");
        user.setSavedMessages(new HashSet<>(Arrays.asList(message1, message2)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<MessageDTO> result = userService.getSavedUserMessages(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRepostedUserMessages(){
        User user = new User();
        user.setId(1L);

        Message message1 = new Message();
        Message message2 = new Message();
        message1.setText("testText1");
        message2.setText("testText2");
        user.setRepostedMessages(new HashSet<>(Arrays.asList(message1, message2)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<MessageDTO> result = userService.getRepostedMessages(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testConvertMessages(){
        User user = new User();
        user.setUsername("testUser");
        Message message1 = new Message();
        message1.setId(1L);
        message1.setText("testText1");
        message1.setAuthor(user);
        Message message2 = new Message();
        message2.setId(2L);
        message2.setText("testText2");
        message2.setAuthor(user);
        List<Message> messages = Arrays.asList(message1, message2);

        List<MessageDTO> result = userService.convertMessages(user, messages, ArrayList::new);

        assertNotNull(result);
        assertEquals(2, result.size());

        MessageDTO resultMessage1 = result.get(0);
        assertEquals(message1.getId(), resultMessage1.getId());
        assertEquals(message1.getText(), resultMessage1.getText());
        assertEquals(user.getUsername(), resultMessage1.getAuthorName());

        MessageDTO resultMessage2 = result.get(1);
        assertEquals(message2.getId(), resultMessage2.getId());
        assertEquals(message2.getText(), resultMessage2.getText());
        assertEquals(user.getUsername(), resultMessage2.getAuthorName());
    }

    @Test
    public void testSubscribe(){
        User currentUser = mock(User.class);
        currentUser.setId(1L);
        User user = mock(User.class);
        user.setId(2L);

        when(userRepository.findById(currentUser.getId())).thenReturn(Optional.of(currentUser));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Set<User> subscribers = new HashSet<>();
        Set<User> subscriptions = new HashSet<>();

        when(userService.getUserSubscribers(currentUser.getId())).thenReturn(subscribers);
        when(userService.getUserSubscriptions(user.getId())).thenReturn(subscriptions);

        userService.subscribe(currentUser, user);

        assertTrue(subscribers.contains(currentUser));
        assertTrue(subscriptions.contains(user));
    }

    @Test
    public void testUnsubscribe(){
        User currentUser = mock(User.class);
        currentUser.setId(1L);
        User user = mock(User.class);
        user.setId(2L);

        when(userRepository.findById(currentUser.getId())).thenReturn(Optional.of(currentUser));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Set<User> subscribers = new HashSet<>();
        subscribers.add(currentUser);
        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(user);

        when(userService.getUserSubscribers(currentUser.getId())).thenReturn(subscribers);
        when(userService.getUserSubscriptions(user.getId())).thenReturn(subscriptions);

        userService.unsubscribe(currentUser, user);

        assertFalse(subscribers.contains(currentUser));
        assertFalse(subscriptions.contains(user));
    }

    @Test
    public void testGetUserSubscribers() {
        User user = mock(User.class);
        user.setId(1L);

        Set<User> subscribers = new HashSet<>();
        subscribers.add(new User());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(user.getSubscribers()).thenReturn(subscribers);

        Set<User> result = userService.getUserSubscribers(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(user, times(1)).getSubscribers();

        assertEquals(subscribers, result);
    }

    @Test
    public void testGetUserSubscriptions() {
        User user = mock(User.class);
        user.setId(1L);

        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(new User());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(user.getSubscriptions()).thenReturn(subscriptions);

        Set<User> result = userService.getUserSubscriptions(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(user, times(1)).getSubscriptions();

        assertEquals(subscriptions, result);
    }

    @Test
    public void testFindSubscriptions(){
        User user = mock(User.class);
        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(new User());
        when(user.getSubscriptions()).thenReturn(subscriptions);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> result = userService.findSubscriptions(user, pageable);

        verify(user, times(1)).getSubscriptions();
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testFindSubscribers(){
        User user = mock(User.class);
        Set<User> subscribers = new HashSet<>();
        subscribers.add(new User());
        when(user.getSubscribers()).thenReturn(subscribers);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> result = userService.findSubscribers(user, pageable);

        verify(user, times(1)).getSubscribers();
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testFindUsers(){
        User user = mock(User.class);
        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(new User());
        when(user.getSubscriptions()).thenReturn(subscriptions);

        Set<User> subscribers = new HashSet<>();
        subscribers.add(new User());
        when(user.getSubscribers()).thenReturn(subscribers);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> resultSubscriptions = userService.findUsers(user, "subscriptions" ,pageable);
        verify(user,times(1)).getSubscriptions();
        assertEquals(1, resultSubscriptions.getTotalElements());

        Page<User> resultSubscribers = userService.findUsers(user, "subscribers" ,pageable);
        verify(user,times(1)).getSubscribers();
        assertEquals(1, resultSubscribers.getTotalElements());
    }
}
