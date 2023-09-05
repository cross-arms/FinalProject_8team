package com.techit.withus.web.feeds.controller.like;

import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.feeds.dto.like.LikeDto.RegisterLikeRequest;
import com.techit.withus.web.feeds.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        @RequestBody RegisterLikeRequest request
    ) {
        likeService.saveLike(request);

        return ResultDTO.builder()
            .message("ok")
            .build();
    }

    @PutMapping("/api/v1/like/cancel")
    public ResultDTO cancelLike(
        @RequestBody RegisterLikeRequest request
    ) {
        likeService.cancelLike(request);

        return ResultDTO.builder()
            .message("ok")
            .build();
    }
}
