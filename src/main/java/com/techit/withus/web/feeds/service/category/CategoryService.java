package com.techit.withus.web.feeds.service.category;

import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.techit.withus.web.feeds.domain.dto.CategoryDto.RetrieveCategoryResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<RetrieveCategoryResponse> retrieveCategories() {
        return categoryRepository.findAll().stream()
                .map(categories -> RetrieveCategoryResponse.fromEntity(categories))
                .collect(Collectors.toList());
    }
}
