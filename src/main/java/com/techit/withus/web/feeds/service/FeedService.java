package com.techit.withus.web.feeds.service;

import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.domain.mapper.FeedMapper;
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
     * 사용자의 홈 피드를 조회하는 메서드.
     *
     * @param userId 조회하려는 사용자의 ID. 만약 null이라면, 모든 최신 피드를 반환한다.
     * @return 조회된 피드 리스트(DTO)
     */
    public List<FeedDTO> getHomeFeeds(Long userId) {
        if (userId != null) {
            List<Feeds> followedFeeds = feedRepository.findFollowedFeeds(userId);
            List<Feeds> allFeeds = feedRepository.findLatestFeeds();
            followedFeeds.addAll(allFeeds);
            return feedMapper.toDto(followedFeeds);
        } else {
            return feedMapper.toDto(feedRepository.findLatestFeeds());
        }
    }

    /**
     * 인기 피드를 페이지 단위로 조회하는 메서드.
     * 이 메서드는 좋아요 수와 댓글 수 등으로 정렬된 인기 피드를 반환한다.
     *
     * @param pageable 페이징 정보를 담고 있는 Pageable 객체.
     * @return 조회된 인기 피드 리스트 (DTO 형태).
     */
    public List<FeedDTO> getPopularfeeds(Pageable pageable) {
        return feedMapper.toDto(feedRepository.findPopularFeeds(pageable));
    }

}
