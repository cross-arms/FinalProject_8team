package com.techit.withus.web.feeds.controller;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.service.FeedService;
import lombok.RequiredArgsConstructor;
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
        List<FeedDTO> feedDTOList = feedService.getFeedsForHome(user);

        return ResultDTO
                .builder()
                .data(feedDTOList)
                .build();
    }
}