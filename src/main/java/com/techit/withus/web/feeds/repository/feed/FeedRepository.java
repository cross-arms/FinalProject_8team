package com.techit.withus.web.feeds.repository.feed;

import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feeds, Long> {
}
