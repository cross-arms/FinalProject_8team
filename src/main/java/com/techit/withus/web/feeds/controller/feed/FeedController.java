package com.techit.withus.web.feeds.controller.feed;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.feeds.domain.dto.FeedDTO;
import com.techit.withus.web.feeds.dto.feed.FeedDto;
import com.techit.withus.web.feeds.service.feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        List<FeedDTO> popularFeedDTOList = feedService.getPopularFeeds(pageable);

        return ResultDTO
                .builder()
                .data(popularFeedDTOList)
                .build();
    }

    @GetMapping("/question")
    public ResultDTO getQuestionFeeds() {

        List<FeedDTO> questionFeedDTOList = feedService.getQuestionFeeds();

        return ResultDTO
                .builder()
                .data(questionFeedDTOList)
                .build();
    }

    @GetMapping("/skill")
    public ResultDTO getFeedsBySkills(@RequestParam(required=false) String large,
                                          @RequestParam(required=false) String medium,
                                          @RequestParam(required=false) String small){
        List<FeedDTO> feedDTOList = feedService.getFeedsBySkills(large, medium, small);

        return ResultDTO
                .builder()
                .data(feedDTOList)
                .build();
    }

    /**
     * 등록된 모든 피드 정보를 조회합니다.
     *
     * @return
     */
    @GetMapping("/api/v1/feeds")
    public Page<FeedDto.FeedResponse> getAllFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedService.getAllFeeds(PageRequest.of(page, size));
    }

    /**
     * 특정 피드 정보를 조회합니다.
     */
    @GetMapping("/api/v1/feeds/{id}")
    public FeedDto.FeedResponse getFeed(
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
    public void createFeed(@RequestBody FeedDto.RegisterFeedRequest registerRequest) {
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
