package com.techit.withus.web.feeds.repository.like;

import com.techit.withus.web.feeds.domain.entity.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Query(value = "select l from Likes l where l.users.userId = :userId and l.feeds.id = :feedId")
    Optional<Likes> findByFeedAndUser(Long feedId, Long userId);
}
