package com.techit.withus.web.feeds.repository.category;

import com.techit.withus.web.feeds.domain.entity.category.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
