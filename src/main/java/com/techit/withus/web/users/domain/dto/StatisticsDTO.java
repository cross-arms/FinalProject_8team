package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsDTO
{
    private Long feedsQuantity;
    private Long chosenCommentsQuantity;
    private Long followerQuantity;
    private Long followQuantity;
}
