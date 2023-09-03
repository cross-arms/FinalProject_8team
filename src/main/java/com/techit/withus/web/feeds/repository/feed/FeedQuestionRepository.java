package com.techit.withus.web.feeds.repository.feed;

import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedQuestionRepository extends JpaRepository<FeedQuestion, Long> {
}
