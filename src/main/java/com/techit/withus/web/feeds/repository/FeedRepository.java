package com.techit.withus.web.feeds.repository;

import com.techit.withus.web.feeds.domain.entity.Feeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feeds, Long> {
    // 모든 사용자의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findLatestFeeds();

    // 팔로우한 사람들의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "JOIN Follows fl ON f.writer.userId = fl.followWho.userId " +
            "WHERE fl.whoFollow.userId = :userId " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findFollowedFeeds(@Param("userId") Long userId);

}
