package com.techit.withus.web.feeds.service;

import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.domain.mapper.FeedMapper;
import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import com.techit.withus.web.feeds.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;

    /**
     * 홈 버튼을 클릭했을 때 호출되는 메서드.
     *
     * @param userId 조회하려는 사용자의 ID. 만약 null이라면, 모든 최신 피드를 반환한다.
     * @return 조회된 홈 피드 리스트(DTO)
     */
    public List<FeedDTO> getHomeFeeds(Long userId) {
        if (userId != null) {
            List<Feeds> followedFeeds = feedRepository.findFollowedFeeds(userId);
            List<Feeds> nonFollowedLatestFeeds = feedRepository.findNonFollowedFeeds(userId);
            followedFeeds.addAll(nonFollowedLatestFeeds);
            return feedMapper.toDto(followedFeeds);
        } else {
            return feedMapper.toDto(feedRepository.findLatestFeeds());
        }
    }

    /**
     * 인기 피드 버튼을 클릭했을 때 페이지 단위로 호출되는 메서드.
     * 이 메서드는 좋아요 수와 댓글 수 등으로 정렬된 인기 피드를 반환한다.
     *
     * @param pageable 페이징 정보를 담고 있는 Pageable 객체.
     * @return 조회된 인기 피드 리스트 (DTO 형태).
     */
    public List<FeedDTO> getPopularFeeds(Pageable pageable) {
        return feedMapper.toDto(feedRepository.findPopularFeeds(pageable));
    }

    /**
     * 질문 피드 버튼을 클릭했을 때 호출되는 메서드.
     * '해결중'과 '해결 완료' 상태에 따라 질문 피드를 조회
     *
     * @return 조회된 질문 피드 리스트(DTO)
     */
    public List<FeedDTO> getQuestionFeeds() {
        List<Feeds> questionsFeeds = feedRepository.findQuestionFeeds(QuestionStatus.RESOLVING, QuestionStatus.RESOLVED);
        return feedMapper.toDto(questionsFeeds);
    }

    /**
     * 선택한 기술 카테고리와 일치하는 피드가 호출되는 메서드.
     */
    public List<FeedDTO> getFeedsBySkills(String large, String medium, String small) {
        List<Feeds> feeds = feedRepository.findFeedsBySkill(large, medium, small);
        return feedMapper.toDto(feeds);
    }
}
