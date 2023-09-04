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
public class ParentComments
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    @OneToMany(mappedBy = "parentComments")
    private List<ChildComments> childCommentsList = new ArrayList<>();

    private String content;

    private String deleteYn;

    public static ParentComments create(Users user, Feeds feed, String content) {
        return ParentComments.builder()
            .users(user)
            .feeds(feed)
            .content(content)
            .deleteYn("N")
            .build();
    }
}