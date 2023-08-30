package com.techit.withus.web.feeds.controller;

import com.techit.withus.web.feeds.dto.FeedDto.RegisterFeedRequest;
import com.techit.withus.web.feeds.dto.FeedDto.RetrieveFeedResponse;
import com.techit.withus.web.feeds.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    /**
     * 등록된 모든 피드 정보를 조회합니다.
     *
     * @return
     */
    @GetMapping("/api/v1/feeds")
    public List<RetrieveFeedResponse> getAllFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        return feedService.getAllFeeds(pageable);
    }

    /**
     * 특정 피드 정보를 조회합니다.
     */
    @GetMapping("/api/v1/feeds/{id}")
    public RetrieveFeedResponse getFeed(
            @PathVariable("id") Long id
    ) {
        return feedService.getFeed(id);
    }

    /**
     * 피드 정보를 등록합니다.
     *
     * @param registerRequest
     * @return
     */
    @PostMapping("/api/v1/feeds")
    public void createFeed(@RequestBody RegisterFeedRequest registerRequest) {
        feedService.saveFeed(registerRequest);
    }

    /**
     * 피드 정보를 수정합니다.
     */
    @PutMapping
    public void modifyFeed() {

    }

    /**
     * 피드 정보를 삭제합니다.
     */
    public void deleteFeed() {

    }

}
