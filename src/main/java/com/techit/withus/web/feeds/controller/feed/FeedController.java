package com.techit.withus.web.feeds.controller.feed;

import com.techit.withus.web.feeds.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.techit.withus.web.feeds.dto.feed.FeedDto.FeedResponse;
import static com.techit.withus.web.feeds.dto.feed.FeedDto.RegisterFeedRequest;

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
    public Page<FeedResponse> getAllFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedService.getAllFeeds(PageRequest.of(page, size));
    }

    /**
     * 특정 피드 정보를 조회합니다.
     */
    @GetMapping("/api/v1/feeds/{id}")
    public FeedResponse getFeed(
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
