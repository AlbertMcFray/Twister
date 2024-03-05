package com.example.twister.Domain.Entity;

import com.example.twister.Domain.Entity.Util.MessageUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Length(max = 2048, message = "Message to long!")
    private String text;
    @Length(max = 2048, message = "Tag to long!")
    private String tag;
    private String filename;
    private LocalDateTime posted;

    //To set date of creation and find tag among text
    @PrePersist
    protected void onCreate(){
        this.posted = LocalDateTime.now();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);
        List<String> tags = new ArrayList<>();
        while (matcher.find()){
            tags.add(matcher.group());
        }
        tag = String.join(", ", tags);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "message_saved",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> saves = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "message_repost",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> reposts = new HashSet<>();

    public String getAuthorName() {
        return MessageUtil.getAuthorName(author);
    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }
}
