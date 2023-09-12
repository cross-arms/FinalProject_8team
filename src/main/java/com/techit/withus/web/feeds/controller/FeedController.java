package com.techit.withus.web.feeds.controller;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.feeds.domain.dto.FeedsDto;
import com.techit.withus.web.feeds.domain.dto.FeedsDto.RegisterFeedRequest;
import com.techit.withus.web.feeds.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/home")
    public ResultDTO getHomeFeeds(@AuthenticationPrincipal SecurityUser user) {
        Long userId = (user != null)? user.getUserId() : null;

        List<FeedsDto.FeedResponse> FeedsDtoList = feedService.getHomeFeeds(userId);

        return ResultDTO
                .builder()
                .data(FeedsDtoList)
                .build();
    }

    @GetMapping("/popular")
    public ResultDTO getPopularFeeds() {
        List<FeedsDto.FeedResponse> popularFeedsDtoList = feedService.getPopularFeeds();

        return ResultDTO
                .builder()
                .data(popularFeedsDtoList)
                .build();
    }


    @GetMapping("/question")
    public ResultDTO getQuestionFeeds() {

        List<FeedsDto.FeedResponse> questionFeedsDtoList = feedService.getQuestionFeeds();

        return ResultDTO
                .builder()
                .data(questionFeedsDtoList)
                .build();
    }

    @GetMapping("/skill/{categoryId}")
    public ResultDTO getFeedsByCategory(@PathVariable Long categoryId) {
        List<FeedsDto.FeedResponse> FeedsDtoList = feedService.getFeedsByCategory(categoryId);

        return ResultDTO
                .builder()
                .data(FeedsDtoList)
                .build();
    }

    /**
     * 등록된 모든 피드 정보를 조회합니다.
     *
     * @return
     */
    @GetMapping("/api/v1/feeds")
    public Page<FeedsDto.FeedResponse> getAllFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedService.getAllFeeds(PageRequest.of(page, size));
    }

    /**
     * 특정 피드 정보를 조회합니다.
     */
    @GetMapping("/api/v1/feeds/{id}")
    public FeedsDto.FeedResponse getFeed(
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
    @PostMapping()
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
