package com.techit.withus.web.comments.domain.entity;

import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child_comments")
public class ChildComments
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_comment_id")
    private ParentComments parentComments;

    private String content;

    private String deleteYn;

    public static ChildComments create(Users user, Feeds feed, ParentComments parentComments,String content) {
        return ChildComments.builder()
            .users(user)
            .feeds(feed)
            .parentComments(parentComments)
            .content(content)
            .deleteYn("N")
            .build();
    }
}