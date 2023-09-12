package com.techit.withus.web.feeds.service;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.web.feeds.domain.dto.FeedsDto;
import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import com.techit.withus.web.feeds.repository.category.CategoryRepository;
import com.techit.withus.web.feeds.repository.feed.FeedQuestionRepository;
import com.techit.withus.web.feeds.repository.feed.FeedRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.techit.withus.common.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static com.techit.withus.common.exception.ErrorCode.FEED_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final CategoryRepository categoryRepository;
    private final FeedQuestionRepository feedQuestionRepository;
    private final UserService userService;

    /**
     * 홈 버튼을 클릭했을 때 호출되는 메서드.
     *
     * @param userId 조회하려는 사용자의 ID. 만약 null이라면, 모든 최신 피드를 반환한다.
     * @return 조회된 홈 피드 리스트(DTO)
     */
    public List<FeedsDto.FeedResponse> getHomeFeeds(Long userId) {
        if (userId != null) {
            List<Feeds> followedFeeds = feedRepository.findFollowedFeeds(userId);
            List<Feeds> nonFollowedLatestFeeds = feedRepository.findNonFollowedFeeds(userId);
            followedFeeds.addAll(nonFollowedLatestFeeds);
            return toResponse(followedFeeds);
        } else {
            return toResponse(feedRepository.findLatestFeeds());
        }
    }

    /**
     * 인기 피드 버튼을 클릭했을 때 페이지 단위로 호출되는 메서드.
     * 이 메서드는 좋아요 수와 댓글 수 등으로 정렬된 인기 피드를 반환한다.
     *
     * @return 조회된 인기 피드 리스트 (DTO 형태).
     */
    public List<FeedsDto.FeedResponse> getPopularFeeds() {
        return toResponse(feedRepository.findPopularFeeds());
    }

    /**
     * 질문 피드 버튼을 클릭했을 때 호출되는 메서드.
     * '해결중'과 '해결 완료' 상태에 따라 질문 피드를 조회
     *
     * @return 조회된 질문 피드 리스트(DTO)
     */
    public List<FeedsDto.FeedResponse> getQuestionFeeds() {
        List<Feeds> questionsFeeds = feedRepository.findQuestionFeeds(QuestionStatus.RESOLVING, QuestionStatus.RESOLVED);
        return toResponse(questionsFeeds);
    }

    /**
     * 선택한 기술 카테고리와 일치하는 피드가 호출되는 메서드.
     */
    public List<FeedsDto.FeedResponse> getFeedsByCategory(Long categoryId) {
        List<Feeds> feeds = feedRepository.findFeedsByCategoryId(categoryId);
        return toResponse(feeds);
    }


    private static List<FeedsDto.FeedResponse> toResponse(List<Feeds> feeds) {
        return feeds.stream()
                .map(FeedsDto.FeedResponse::toDtoFrom)
                .collect(Collectors.toList());
    }


    public Page<FeedsDto.FeedResponse> getAllFeeds(PageRequest pageable) {
        Page<Feeds> feeds = feedRepository.findAll(pageable);

        return PageableExecutionUtils.getPage(toResponse(feeds), pageable, feeds::getTotalElements);
    }

    private static List<FeedsDto.FeedResponse> toResponse(Page<Feeds> feeds) {
        return feeds.getContent().stream()
                .map(FeedsDto.FeedResponse::toDtoFrom)
                .collect(Collectors.toList());
    }

    public FeedsDto.FeedResponse getFeed(Long feedId) {
        return FeedsDto.FeedResponse.toDtoFrom(findFeed(feedId));
    }

    @Transactional
    public void saveFeed(FeedsDto.RegisterFeedRequest request) {
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

    public Feeds findFeed(Long feedId) {
        return feedRepository.findById(feedId).orElseThrow(
                () -> new EntityNotFoundException(FEED_NOT_FOUND)
        );
    }
}



