package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO
{
    private Long userId;
    private String username;
    private String profileURL;
    private String oneLineIntroduction;
}
