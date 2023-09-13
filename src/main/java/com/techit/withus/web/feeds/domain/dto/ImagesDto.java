package com.techit.withus.web.feeds.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ImagesDto {

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class ImagesResponse {

        private String imageURL;
    }
}
