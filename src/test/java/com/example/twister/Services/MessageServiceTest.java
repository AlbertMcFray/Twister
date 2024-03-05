package com.example.twister.Services;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Entity.Util.MessageUtil;
import com.example.twister.Domain.Repositories.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.util.UriComponents;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    public void testGetMessagesByFilter(){
        User user = new User();
        String filterTag = "test";
        Message message = new Message();
        message.setTag(filterTag);
        List<Message> messages = Arrays.asList(message);

        when(messageRepository.findByTag(filterTag)).thenReturn(messages);

        List<MessageDTO> result = messageService.getMessagesByFilter(user, filterTag);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(MessageUtil.convertToDTO(user, message), result.get(0));
        verify(messageRepository, times(1)).findByTag(filterTag);
    }

    @Test
    public void testAddMessage(){
        User user = new User();
        user.setId(1L);
        Message message = new Message();
        message.setAuthor(user);
        message.setText("Test message");

        byte[] imageContent = "fake image content".getBytes();
        MultipartFile file = new MockMultipartFile("imagefile", "hello.png", "image/png", imageContent);


        ReflectionTestUtils.setField(messageService, "uploadPath", "/path/to/upload");
        messageService.addMessage(message, user, Optional.of(file));

        assertEquals(user, message.getAuthor());
        assertNotNull(message.getFilename());
        verify(messageRepository, times(1)).save(message);
    }


    @Test
    public void testProcessFile(){
        Message message = new Message();
        byte[] imageContent = "fake image content".getBytes(); // здесь должны быть реальные байты изображения
        MultipartFile file = new MockMultipartFile("imagefile", "hello.png", "image/png", imageContent);

        ReflectionTestUtils.setField(messageService, "uploadPath", "/path/to/upload");
        messageService.processFile(message, Optional.of(file));

        assertNotNull(message.getFilename());
    }

    @Test
    public void testGetUriComponents(){
        User currentUser = new User();
        Set<User> userSet = new HashSet<>();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String referer = "http://localhost:8080";

        UriComponents result = messageService.getUriComponents(currentUser, userSet, redirectAttributes, referer);

        assertNotNull(result);
        assertEquals(referer, result.toUriString());
    }
}
