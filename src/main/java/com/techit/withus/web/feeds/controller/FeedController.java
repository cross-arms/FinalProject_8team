package com.techit.withus.web.feeds.controller;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/home")
    public ResultDTO getHomeFeeds(@AuthenticationPrincipal SecurityUser user) {
        Long userId = (user != null)? user.getUserId() : null;

        List<FeedDTO> feedDTOList = feedService.getHomeFeeds(userId);

        return ResultDTO
                .builder()
                .data(feedDTOList)
                .build();
    }

    @GetMapping("/popular")
    public ResultDTO getPopularFeeds() {
        // 1번째 페이지 상위 10개 피드
        Pageable pageable = PageRequest.of(0, 10);

        List<FeedDTO> popularFeedDTOList = feedService.getPopularfeeds(pageable);

        return ResultDTO
                .builder()
                .data(popularFeedDTOList)
                .build();
    }
}
