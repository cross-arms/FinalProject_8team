package com.techit.withus.web.feeds.repository;

import com.techit.withus.web.feeds.domain.entity.Feeds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feeds, Long> {
}
