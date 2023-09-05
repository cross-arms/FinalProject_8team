package com.techit.withus.web.feeds.service.feed;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.dto.feed.FeedDto;
import com.techit.withus.web.feeds.dto.feed.FeedDto.FeedResponse;
import com.techit.withus.web.feeds.dto.feed.FeedDto.RegisterFeedRequest;
import com.techit.withus.web.feeds.repository.category.CategoryRepository;
import com.techit.withus.web.feeds.repository.feed.FeedQuestionRepository;
import com.techit.withus.web.feeds.repository.feed.FeedRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.techit.withus.common.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static com.techit.withus.common.exception.ErrorCode.FEED_NOT_FOUND;


@RequiredArgsConstructor
@Service
public class FeedService {

    private final CategoryRepository categoryRepository;
    private final FeedRepository feedRepository;
    private final FeedQuestionRepository feedQuestionRepository;
    private final UserService userService;

    public Page<FeedResponse> getAllFeeds(PageRequest pageable) {
        Page<Feeds> feeds = feedRepository.findAll(pageable);

        return PageableExecutionUtils.getPage(toResponse(feeds), pageable, feeds::getTotalElements);
    }

    private static List<FeedResponse> toResponse(Page<Feeds> feeds) {
        return feeds.getContent().stream()
                .map(FeedResponse::toDtoFrom)
                .collect(Collectors.toList());
    }

    public FeedResponse getFeed(Long feedId) {
        return FeedResponse.toDtoFrom(findFeed(feedId));
    }

    @Transactional
    public void saveFeed(RegisterFeedRequest request) {
        // 피드를 등록할 수 있음.
        Users user = Users.fromDto(userService.getUserInfoBy(request.getUserId()));

        Categories category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new EntityNotFoundException(CATEGORY_NOT_FOUND)
        );

        // request to Feeds
        Feeds feed = request.toFeedsEntity(user, category);

        feedRepository.save(feed);

        if (request.isQuestionFeed()) {
            request.questionFeedValidation();

            FeedQuestion feedsQuestion = request.toFeedsQuestionEntity(feed);
            feed.setFeedQuestion(feedsQuestion);

            feedQuestionRepository.save(feedsQuestion);

            feed.updateScopeToPublic();
        }
    }

    public Feeds getFeedBy(Long feedId) {
        return findFeed(feedId);
    }

    private Feeds findFeed(Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(
                () -> new EntityNotFoundException(FEED_NOT_FOUND)
        );
    }
}
