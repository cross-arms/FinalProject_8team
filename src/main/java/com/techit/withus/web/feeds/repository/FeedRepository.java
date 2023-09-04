package com.techit.withus.web.feeds.repository;

import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feeds, Long> {
    // 모든 사용자의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findLatestFeeds();

    // 팔로우하지 않은 사람들의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images i "+
            "WHERE (:userId IS NULL OR NOT EXISTS (SELECT 1 FROM Follows fl WHERE fl.followWho.userId = :userId AND fl.whoFollow.userId = f.writer.userId))"+
            "ORDER BY f.createdDate DESC")
    List<Feeds> findNonFollowedFeeds(@Param("userId") Long userId);

    // 팔로우한 사람들의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "JOIN Follows fl ON f.writer.userId = fl.followWho.userId " +
            "LEFT JOIN FETCH f.images i " +
            "WHERE fl.whoFollow.userId = :userId " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findFollowedFeeds(@Param("userId") Long userId);

    /**
     * 좋아요와 댓글 수를 고려하여 인기 피드를 조회하는 메서드
     * [우선 순위]
     * 1. 좋아요가 가장 많은 피드.
     * 2. 좋아요 수가 같다면, 댓글이 가장 많은 피드.
     * 3. 좋아요 수와 댓글 수가 모두 같다면, 가장 최신에 작성된 피드.
     */
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN Likes l ON f.feedId = l.feeds.feedId " +
            "LEFT JOIN Comments c ON f.feedId = c.feeds.feedId " +
            "LEFT JOIN FETCH f.images " +
            "GROUP BY f.feedId, f.createdDate " +
            "ORDER BY COUNT(DISTINCT l.likeId) DESC, COUNT(DISTINCT c.commentId) DESC, f.createdDate DESC")
    List<Feeds> findPopularFeeds(Pageable pageable);

    /**
     * '해결중'과 '해결 완료' 상태에 따라 질문 피드를 조회하는 메서드
     * [우선 순위]
     * 1. '해결중' 상태
     * 2. '해결 완료' 상태
     */
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images " +
            "LEFT JOIN Questions q ON f.feedId = q.feeds.feedId " +
            "WHERE q.status IN (:resolving, :resolved) "+
            "ORDER BY CASE WHEN q.status = :resolving THEN 1 WHEN q.status = :resolved THEN 2 ELSE 3 END ASC,"+
            "f.createdDate DESC")
    List<Feeds> findQuestionFeeds(@Param("resolving") QuestionStatus resolving, @Param("resolved") QuestionStatus resolved);


    /**
     * 카테고리별로 피드를 조회하는 메서드
     * [우선 순위]
     * 1. 소분류 일치
     * 2. 중분류 일치
     * 3. 대분류 일치
     * 모든 조건이 동일하면 최신 피드부터 반환
     */
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images i "+
            "JOIN FeedCategories fc ON fc.feeds = f "+
            "WHERE (:small IS NULL OR fc.categories.small = :small) "+
            "AND (:medium IS NULL OR fc.categories.medium = :medium) "+
            "AND (:large IS NULL OR fc.categories.large = :large) "+
            "ORDER BY CASE WHEN fc.categories.small = :small THEN 1 WHEN fc.categories.medium = :medium THEN 2 WHEN fc.categories.large = :large THEN 3 ELSE 4 END ASC,"+
            "f.createdDate DESC")
    List<Feeds> findFeedsBySkill(@Param("large") String large, @Param("medium") String medium, @Param("small") String small);
}
