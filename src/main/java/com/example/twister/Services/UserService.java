package com.example.twister.Services;

import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Entity.Util.MessageUtil;
import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getUserList() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public List<MessageDTO> getUserMessages(Long userId) {
        User user = getUser(userId);
        return convertMessages(user, user.getMessages(), ArrayList::new);
    }

    public Set<MessageDTO> getSavedUserMessages(Long userId) {
        User user = getUser(userId);
        return convertMessages(user, user.getSavedMessages(), HashSet::new);
    }

    public Set<MessageDTO> getRepostedMessages(Long userId){
        User user = getUser(userId);
        return convertMessages(user, user.getRepostedMessages(), HashSet::new);
    }

    private User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public <T extends Collection<MessageDTO>> T convertMessages(User user, Collection<Message> messages, Supplier<T> collectionFactory){
        return messages.stream()
                .map(message -> MessageUtil.convertToDTO(user, message))
                .collect(Collectors.toCollection(collectionFactory));
    }

    @Transactional
    public void subscribe(User currentUser, User user) {
        getUserSubscribers(user.getId()).add(currentUser);
        getUserSubscriptions(currentUser.getId()).add(user);
    }

    @Transactional
    public void unsubscribe(User currentUser, User user) {
        getUserSubscribers(user.getId()).remove(currentUser);
        getUserSubscriptions(currentUser.getId()).remove(user);
    }

    @Transactional
    public Set<User> getUserSubscribers(Long userId) {
        User user = getUser(userId);
        return user.getSubscribers();
    }

    @Transactional
    public Set<User> getUserSubscriptions(Long userId) {
        User user = getUser(userId);
        return user.getSubscriptions();
    }

    @Transactional
    public Map<String, Integer> getSubs(User user){
        Map<String, Integer> subsData = new HashMap<>();
        subsData.put("subscribers", getUserSubscribers(user.getId()).size());
        subsData.put("subscriptions", getUserSubscriptions(user.getId()).size());
        return subsData;
    }

    public Page<User> findSubscriptions(User user, Pageable pageable) {
        List<User> subscriptions = new ArrayList<>(user.getSubscriptions());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), subscriptions.size());
        return new PageImpl<>(subscriptions.subList(start, end), pageable, subscriptions.size());
    }

    public Page<User> findSubscribers(User user, Pageable pageable) {
        List<User> subscribers = new ArrayList<>(user.getSubscribers());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), subscribers.size());
        return new PageImpl<>(subscribers.subList(start, end), pageable, subscribers.size());
    }

    public Page<User> findUsers(User user, String type, Pageable pageable) {
        Page<User> usersPage;
        if("subscriptions".equals(type)){
            usersPage = findSubscriptions(user, pageable);
        } else {
            usersPage = findSubscribers(user, pageable);
        }
        return usersPage;
    }
}