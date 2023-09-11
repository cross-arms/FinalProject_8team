package com.techit.withus.web.feeds.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedType {

    NORMAL("일반피드"),
    QUESTION("질문피드"),
    ;

    private final String description;
}
