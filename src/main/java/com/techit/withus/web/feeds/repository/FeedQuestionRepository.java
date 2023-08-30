package com.techit.withus.web.feeds.repository;

import com.techit.withus.web.feeds.domain.entity.FeedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedQuestionRepository extends JpaRepository<FeedQuestion, Long> {
}
