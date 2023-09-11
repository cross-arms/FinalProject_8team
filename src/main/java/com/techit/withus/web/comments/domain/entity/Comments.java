package com.techit.withus.web.comments.domain.entity;

import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parent_comments")
public class Comments
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    @Builder.Default
    @OneToMany(mappedBy = "comments")
    private List<ChildComments> childCommentsList = new ArrayList<>();

    private String content;

    private String deleteYn;

    public static Comments create(Users user, Feeds feed, String content) {
        return Comments.builder()
            .users(user)
            .feeds(feed)
            .content(content)
            .deleteYn("N")
            .build();
    }
}