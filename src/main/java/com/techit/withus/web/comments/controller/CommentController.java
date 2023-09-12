package com.techit.withus.web.comments.controller;


import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.comments.service.CommentService;
import com.techit.withus.web.feeds.domain.dto.FeedsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.techit.withus.web.comments.dto.CommentDto.*;
import static com.techit.withus.web.comments.dto.CommentDto.ModifyParentCommentRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 피드의 모든 댓글 조회
     */
    @GetMapping("/api/v1/feeds/{feedId}/comments")
    public Page<FeedsDto.FeedResponse> getAllCommentsByFeedId(
            @PathVariable("feedId") Long feedId,
            @RequestParam(defaultValue = "10") int size
    ) {
        return commentService.getAllComments(feedId, Pageable.ofSize(size));
    }

    /**
     * 피드에 댓글, 대댓글 등록
     */
    @PostMapping("/api/v1/feeds/comments")
    public ResultDTO saveComment(
            @RequestBody RegisterParentCommentRequest request,
            @AuthenticationPrincipal SecurityUser user
    ) {
        request.setUserId(user.getUserId());

        commentService.registerComment(request);

        return ResultDTO.builder()
                .message("OK")
                .build();
    }

    /**
     * 댓글 수정
     *
     * @param request
     * @param user
     * @return
     */
    @PutMapping("/api/v1/feeds/comments")
    public ResultDTO updateComment(
            @RequestBody ModifyParentCommentRequest request,
            @AuthenticationPrincipal SecurityUser user
    ) {
        commentService.updateComment(request);

        return ResultDTO.builder()
                .message("OK")
                .build();
    }

    /**
     * 댓글 삭제
     *
     * @param request
     * @param user
     * @return
     */
    @DeleteMapping("/api/v1/feeds/comments")
    public ResultDTO deleteComment(
            @RequestBody DeleteParentCommentRequest request,
            @AuthenticationPrincipal SecurityUser user
    ) {

        commentService.deleteComment(request.getCommentId(), request.getFeedId());

        return ResultDTO.builder()
                .message("OK")
                .build();
    }
}
