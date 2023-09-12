package com.techit.withus.web.comments.repository;

import com.techit.withus.web.comments.domain.entity.Comments;
import com.techit.withus.web.feeds.domain.dto.FeedsDto.FeedResponse;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    Page<FeedResponse> findAllByFeeds(Feeds feeds, Pageable pageRequest);
}
