package com.techit.withus.web.feeds.domain.dto;

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

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
