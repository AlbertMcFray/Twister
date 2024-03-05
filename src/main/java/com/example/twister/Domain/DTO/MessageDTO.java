package com.example.twister.Domain.DTO;

import com.example.twister.Domain.Entity.User;
import com.example.twister.Domain.Entity.Util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String text;
    private String tag;
    private String filename;
    private User author;
    private Long likes;
    private Boolean isLiked;
    private Long saves;
    private Boolean isSaved;
    private Long reposts;
    private Boolean isReposted;
    private LocalDateTime posted;
    public String getAuthorName() {
        return MessageUtil.getAuthorName(author);
    }
}
