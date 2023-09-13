package com.techit.withus.web.feeds.repository.feed;

import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import com.techit.withus.web.users.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feeds, Long> {
    // 모든 사용자의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findLatestFeeds();

    // 팔로우하지 않은 사람들의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images i "+
            "WHERE (:userId IS NULL OR NOT EXISTS (SELECT 1 FROM Follows fl WHERE fl.followWho.userId = :userId AND fl.whoFollow.userId = f.writer.userId)) "+
            "AND f.deleteYn = 'N' " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findNonFollowedFeeds(@Param("userId") Long userId);

    // 팔로우한 사람들의 최신 피드를 조회하는 메서드
    @Query("SELECT f FROM Feeds f " +
            "JOIN Follows fl ON f.writer.userId = fl.followWho.userId " +
            "LEFT JOIN FETCH f.images i " +
            "WHERE fl.whoFollow.userId = :userId AND f.deleteYn = 'N' " +
            "ORDER BY f.createdDate DESC")
    List<Feeds> findFollowedFeeds(@Param("userId") Long userId);

    /**
     * 좋아요와 댓글 수를 고려하여 인기 피드를 조회하는 메서드
     * [우선 순위]
     * 1. 좋아요가 가장 많은 피드.
     * 2. 좋아요 수가 같다면, 댓글이 가장 많은 피드.
     * 3. 좋아요 수와 댓글 수가 모두 같다면, 가장 최신에 작성된 피드.
     */
//    @Query("SELECT f FROM Feeds f " +
//            "LEFT JOIN Likes l ON f.feedId = l.feeds.feedId " +
//            "LEFT JOIN Comments c ON f.feedId = c.feeds.feedId " +
//            "LEFT JOIN FETCH f.images " +
//            "GROUP BY f.feedId, f.createdDate " +
//            "ORDER BY COUNT(DISTINCT l.likeId) DESC, COUNT(DISTINCT c.commentId) DESC, f.createdDate DESC")
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images " +
            "WHERE (SELECT COUNT(l) FROM Likes l WHERE l.feeds = f AND l.cancelYn = 'N') > 0 OR " +
            "(SELECT COUNT(c) FROM Comments c WHERE c.feeds = f AND c.deleteYn = 'N') > 0 " +
            "ORDER BY (SELECT COUNT(l) FROM Likes l WHERE l.feeds = f AND l.cancelYn = 'N') DESC, " +
            "(SELECT COUNT(c) FROM Comments c WHERE c.feeds = f AND c.deleteYn = 'N') DESC, f.createdDate DESC")
    List<Feeds> findPopularFeeds();

    /**
     * '해결중'과 '해결 완료' 상태에 따라 질문 피드를 조회하는 메서드
     * [우선 순위]
     * 1. '해결중' 상태
     * 2. '해결 완료' 상태
     */
    @Query("SELECT f FROM Feeds f " +
            "LEFT JOIN FETCH f.images " +
            "LEFT JOIN FeedQuestion q ON f.feedId = q.feeds.feedId " +
            "WHERE q.status IN (:resolving, :resolved) AND f.deleteYn = 'N' AND f.type = com.techit.withus.web.feeds.enumeration.FeedType.QUESTION "+
            "ORDER BY CASE WHEN q.status = :resolving THEN 1 WHEN q.status = :resolved THEN 2 ELSE 3 END ASC,"+
            "f.createdDate DESC")
    List<Feeds> findQuestionFeeds(@Param("resolving") QuestionStatus resolving, @Param("resolved") QuestionStatus resolved);



    /**
     * 카테고리별로 피드를 조회하는 메서드
     * 모든 조건이 동일하면 최신 피드부터 반환
     */
    @Query("SELECT f FROM Feeds f JOIN f.category c WHERE c.categoryId = :categoryId AND f.deleteYn = 'N' ORDER BY f.createdDate DESC")
    List<Feeds> findFeedsByCategoryId(@Param("categoryId") Long categoryId);

    Optional<Feeds> findByFeedIdAndWriter(Long feedId, Users user);
    Long countAllByWriter(Users users);

    List<Feeds> findAllByWriter(Users users);

    @Query("SELECT f FROM Feeds f WHERE f.title ILIKE :query OR f.content ILIKE :query")
    List<Feeds> findAllByQuery(String query);
}
