package com.techit.withus.web.feeds.dto.like;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class LikeDto {

    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterLikeRequest {

        private Long feedId;
        private Long userId;
    }
}
