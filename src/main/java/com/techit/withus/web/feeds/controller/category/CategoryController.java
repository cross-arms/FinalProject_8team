package com.techit.withus.web.feeds.controller.category;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.web.feeds.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/v1/categories")
    public ResultDTO retrieveCategories() {

        return ResultDTO.builder()
                .data(categoryService.retrieveCategories())
                .build();
    }

}
