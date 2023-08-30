package com.techit.withus.web.feeds.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedScope {

    PRIVATE("비공개"),
    PUBLIC("전체 공개"),
    FOLLOWING("팔로워만 공개"),
    ;

    private final String description;
}
