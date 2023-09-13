package com.techit.withus.web.users.domain.dto;

import com.techit.withus.web.users.domain.entity.Users;
import lombok.*;

public class UserDto {

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {

        private Long userId;
        private String username;
        private String profileURL;
        private String oneLineIntroduction;

        public static UserResponse create(Users user) {
            return UserResponse.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .profileURL(user.getProfileURL())
                    .oneLineIntroduction(user.getOneLineIntroduction())
                    .build();
        }
    }
}
