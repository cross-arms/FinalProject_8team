package com.techit.withus.web.feeds.service;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.domain.mapper.FeedMapper;
import com.techit.withus.web.feeds.repository.FeedRepository;
import com.techit.withus.web.users.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;

    // 홈 버튼을 눌렀을 때 피드를 가져오는 메서드
    public List<FeedDTO> getFeedsForHome(@AuthenticationPrincipal SecurityUser user) {
        // 현재 로그인한 사용자가 팔로우한 사람들의 최신 피드 조회 (1순위)
        List<Feeds> followedFeeds = feedRepository.findFollowedFeeds((List<Users>) user);

        // 모든 사용자의 최신 피드 조회 (2순위)
        List<Feeds> allFeeds = feedRepository.findLatestFeeds();

        // 팔로우한 사람의 피드와 모든 피드를 합치고 정렬하여 반환
        followedFeeds.addAll(allFeeds);
        followedFeeds.sort((feed1, feed2) -> feed2.getCreatedDate().compareTo(feed1.getCreatedDate()));

        return FeedMapper.INSTANCE.toFeedDTOList(followedFeeds);
    }

}
