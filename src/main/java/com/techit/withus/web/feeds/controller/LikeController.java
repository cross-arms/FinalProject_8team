package com.techit.withus.web.feeds.controller;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.feeds.domain.dto.LikeDto.RegisterLikeRequest;
import com.techit.withus.web.feeds.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PutMapping("/api/v1/like")
    public ResultDTO saveLike(
        @RequestBody RegisterLikeRequest request,
        @AuthenticationPrincipal SecurityUser user
    ) {
        request.setUserId(user.getUserId());

        likeService.saveLike(request);

        return ResultDTO.builder()
            .message("ok")
            .build();
    }

    @PutMapping("/api/v1/like/cancel")
    public ResultDTO cancelLike(
        @RequestBody RegisterLikeRequest request,
        @AuthenticationPrincipal SecurityUser user
    ) {
        request.setUserId(user.getUserId());

        likeService.cancelLike(request);

        return ResultDTO.builder()
            .message("ok")
            .build();
    }
}
