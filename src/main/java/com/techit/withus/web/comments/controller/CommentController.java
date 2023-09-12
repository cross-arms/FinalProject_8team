package com.techit.withus.web.comments.controller;


import com.techit.withus.web.comments.service.CommentService;
import com.techit.withus.web.feeds.domain.dto.FeedsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 피드의 모든 댓글 조회
     */
    @GetMapping("/api/v1/feeds/comments")
    public Page<FeedsDto.FeedResponse> getAllComments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return commentService.getAllComments(PageRequest.of(page, size));
    }

    /**
     * 피드에 댓글 달기
     */


    /**
     * 댓글에 대댓글 달기
     */
}
