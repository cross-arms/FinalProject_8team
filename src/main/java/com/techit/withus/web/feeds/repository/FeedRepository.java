package com.techit.withus.web.feeds.repository;

import com.techit.withus.web.feeds.domain.entity.Feeds;
import org.springframework.data.domain.Pageable;
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

    // 좋아요와 댓글 수를 고려하여 인기 피드를 조회하는 메서드
    /*
        1. 좋아요가 가장 많은 피드.
        2. 좋아요 수가 같다면, 댓글이 가장 많은 피드.
        3. 좋아요 수와 댓글 수가 모두 같다면, 가장 최신에 작성된 피드.
     */
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN Likes l ON f.feedId = l.feeds.feedId " +
            "LEFT JOIN Comments c ON f.feedId = c.feeds.feedId " +
            "GROUP BY f.feedId, f.createdDate " +
            "ORDER BY COUNT(DISTINCT l.likeId) DESC, COUNT(DISTINCT c.commentId) DESC, f.createdDate DESC")
    List<Feeds> findPopularFeeds(Pageable pageable);

}
