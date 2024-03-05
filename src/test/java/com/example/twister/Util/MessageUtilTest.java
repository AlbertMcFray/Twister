package com.example.twister.Util;

import com.example.twister.Domain.DTO.MessageDTO;
import com.example.twister.Domain.Entity.Message;
import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Entity.Util.MessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageUtilTest {

    @Test
    public void testGetAuthorName(){
        User user = new User();
        user.setUsername("testUser");
        assertEquals("testUser", MessageUtil.getAuthorName(user));
        assertEquals("<none>", MessageUtil.getAuthorName(null));
    }

    @Test
    public void testConvertToDTO() {
        User currentUser = new User();
        currentUser.setUsername("testUser");
        Message message = new Message();
        message.setId(1L);
        message.setText("testText");
        message.setTag("testTag");
        message.setAuthor(currentUser);
        message.setFilename("testFilename");
        message.setLikes(new HashSet<>(Arrays.asList(currentUser)));
        message.setSaves(new HashSet<>(Arrays.asList(currentUser)));
        message.setReposts(new HashSet<>(Arrays.asList(currentUser)));
        message.setPosted(LocalDateTime.now());

        MessageDTO result = MessageUtil.convertToDTO(currentUser, message);

        assertNotNull(result);
        assertEquals(message.getId(), result.getId());
        assertEquals(message.getText(), result.getText());
        assertEquals(message.getTag(), result.getTag());
        assertEquals(message.getAuthor(), result.getAuthor());
        assertEquals(message.getFilename(), result.getFilename());
        assertEquals(Long.valueOf(message.getLikes().size()), Long.valueOf(result.getLikes()));
        assertTrue(result.getIsLiked());
        assertEquals(Long.valueOf(message.getSaves().size()), result.getSaves());
        assertTrue(result.getIsSaved());
        assertEquals(Long.valueOf(message.getReposts().size()), result.getReposts());
        assertTrue(result.getIsReposted());
        assertEquals(message.getPosted(), result.getPosted());
    }
}
