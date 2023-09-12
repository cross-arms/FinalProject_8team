package com.techit.withus.web.feeds.domain.dto;

import com.sun.mail.iap.Response;
import com.techit.withus.web.feeds.domain.entity.category.Categories;
import lombok.*;

public class CategoryDto {

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrieveCategoryResponse {

        private String name;
        private String categoryImageURL;

        public static RetrieveCategoryResponse fromEntity(Categories entity) {
            return RetrieveCategoryResponse.builder()
                    .name(entity.getName())
                    .categoryImageURL(entity.getImageURL())
                    .build();
        }
    }
}
