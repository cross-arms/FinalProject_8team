package com.techit.withus.web.feeds.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedDTO {
    private Long feedId;
    private String writer;
    private String scope;
    private String title;
    private String content;
    // 좋아요 수, 댓글 수
    private Long likeCount;
    private Long commentCount;
    // 이미지 URL
    List<String> imageUrls;
}
