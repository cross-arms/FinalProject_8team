package com.techit.withus.web.feeds.service;

import com.techit.withus.web.feeds.domain.entity.FeedQuestion;
import com.techit.withus.web.feeds.repository.FeedRepository;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.dto.FeedDto.RetrieveFeedResponse;
import com.techit.withus.web.feeds.repository.FeedQuestionRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.techit.withus.web.feeds.dto.FeedDto.RegisterFeedRequest;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedQuestionRepository feedQuestionRepository;
    private final UserService userService;

    public List<RetrieveFeedResponse> getAllFeeds(PageRequest pageable) {
        Page<Feeds> feeds = feedRepository.findAll(pageable);
        Pageable page = feeds.getPageable();
        return null;
    }

    public RetrieveFeedResponse getFeed(Long id) {
        return null;
    }

    @Transactional
    public void saveFeed(RegisterFeedRequest request) {
        // 피드를 등록할 수 있음.
        Users user = userService.getUserInfo(request.getUserId());

        // request to Feeds
        Feeds feed = request.toFeedsEntity(user);

        feedRepository.save(feed);

        if (request.isQuestionFeed()) {
            request.questionFeedValidation();

            FeedQuestion feedsQuestion = request.toFeedsQuestionEntity(feed);
            feed.setFeedQuestion(feedsQuestion);

            feedQuestionRepository.save(feedsQuestion);

            feed.updateScopeToPublic();
        }
    }
}
