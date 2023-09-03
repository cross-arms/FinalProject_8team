package com.techit.withus.web.feeds.repository.like;

import com.techit.withus.web.feeds.domain.entity.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByFeedIdAndUserId(Long feedId, Long userId);
}
