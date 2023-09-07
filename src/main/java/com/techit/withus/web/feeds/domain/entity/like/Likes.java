package com.techit.withus.web.feeds.domain.entity.like;

import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.dto.feed.FeedDto;
import com.techit.withus.web.feeds.dto.feed.FeedDto.FeedResponse;
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
@Table(name = "likes")
public class Likes {

    public static final String LIKE = "N";
    public static final String CANCEL = "Y";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    public static Likes createLike(Feeds feed) {
        return Likes.builder()
                .users(feed.getWriter())
                .feeds(feed)
                .likeStatus(LikeStatus.LIKE)
                .build();
    }

    public void toUnlike() {
        this.likeStatus = LikeStatus.UN_LIKE;
    }

    public boolean isAlreadyLike() {
        return likeStatus == LikeStatus.LIKE;
    }

    public enum LikeStatus {
        LIKE,
        UN_LIKE
    }
}
