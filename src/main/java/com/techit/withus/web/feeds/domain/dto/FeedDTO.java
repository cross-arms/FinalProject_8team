package com.techit.withus.web.feeds.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedDTO {
    private Long feedId;
    private Long writerId;
    private String writer;
    private String scope;
    private String title;
}
