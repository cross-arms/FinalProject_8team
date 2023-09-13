package com.techit.withus.web.feeds.service;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.domain.entity.feed.Images;
import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import com.techit.withus.web.feeds.exception.FeedException;
import com.techit.withus.web.feeds.repository.category.CategoryRepository;
import com.techit.withus.web.feeds.repository.feed.FeedQuestionRepository;
import com.techit.withus.web.feeds.repository.feed.FeedRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.repository.UserRepository;
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
import static com.techit.withus.web.feeds.domain.dto.FeedsDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final CategoryRepository categoryRepository;
    private final FeedQuestionRepository feedQuestionRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public void updateFeed(ModifyFeedRequest request) {
        Users user = userService.findUser(request.getUserId());

        Feeds feeds = feedRepository.findByFeedIdAndWriter(request.getFeedId(), user).orElseThrow(
            () -> new FeedException(FEED_NOT_FOUND)
        );

        feeds.modifyFeed(request);
    }

    @Transactional
    public void deleteFeed(ModifyFeedRequest request) {
        Users user = userService.findUser(request.getUserId());

        Feeds feeds = feedRepository.findByFeedIdAndWriter(request.getFeedId(), user).orElseThrow(
            () -> new FeedException(FEED_NOT_FOUND)
        );

        feeds.delete();
    }

    /**
     * 홈 버튼을 클릭했을 때 호출되는 메서드.
     *
     * @param userId 조회하려는 사용자의 ID. 만약 null이라면, 모든 최신 피드를 반환한다.
     * @return 조회된 홈 피드 리스트(DTO)
     */
    public List<FeedResponse> getHomeFeeds(Long userId) {
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
    public List<FeedResponse> getPopularFeeds() {
        return toResponse(feedRepository.findPopularFeeds());
    }

    /**
     * 질문 피드 버튼을 클릭했을 때 호출되는 메서드.
     * '해결중'과 '해결 완료' 상태에 따라 질문 피드를 조회
     *
     * @return 조회된 질문 피드 리스트(DTO)
     */
    public List<FeedResponse> getQuestionFeeds() {
        List<Feeds> questionsFeeds = feedRepository.findQuestionFeeds(QuestionStatus.RESOLVING, QuestionStatus.RESOLVED);
        return toResponse(questionsFeeds);
    }

    /**
     * 선택한 기술 카테고리와 일치하는 피드가 호출되는 메서드.
     */
    public List<FeedResponse> getFeedsByCategory(Long categoryId) {
        List<Feeds> feeds = feedRepository.findFeedsByCategoryId(categoryId);
        return toResponse(feeds);
    }


    private static List<FeedResponse> toResponse(List<Feeds> feeds) {
        return feeds.stream()
                .map(FeedResponse::toDtoFrom)
                .collect(Collectors.toList());
    }


    public Page<FeedResponse> getAllFeeds(PageRequest pageable) {
        Page<Feeds> feeds = feedRepository.findAll(pageable);

        return PageableExecutionUtils.getPage(toResponse(feeds), pageable, feeds::getTotalElements);
    }

    private static List<FeedResponse> toResponse(Page<Feeds> feeds) {
        return feeds.getContent().stream()
                .map(FeedResponse::toDtoFrom)
                .collect(Collectors.toList());
    }

    /**
     * 피드 정보를 조회한다.
     * 피드 id 를 In 절 쿼리로 피드 이미지 정보를 조회한다.
     *
     * @param feedId
     * @return
     */
    public FeedResponse getFeed(Long feedId) {
        // TODO
        // 1. 피드 정보 조회
        // 2. 피드 정보 기반으로 피드 이미지 정보 조회
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

        if (request.getImageUrl().size() > 0) {
            List<Images> images = request.getImageUrl().stream()
                    .map(imageUrl -> Images.builder()
                            .feeds(feed)
                            .imageURL(imageUrl)
                            .build())
                    .collect(Collectors.toList());

            feed.setImageList(images);
        }

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

    public List<FeedResponse> getAllFeedsByQuery(String query)
    {
        List<Feeds> feedEntities = feedRepository.findAllByQuery(String.format("%s%s%s", "%", query, "%"));
        return toResponse(feedEntities);
    }

    @Transactional
    public List<FeedResponse> getAllFeedsByUserId(Long userId)
    {
        List<Feeds> feedEntities = feedRepository.findAllByWriter(
                userRepository.getReferenceById(userId)
        );

        return feedEntities
                .stream()
                .map(FeedResponse::toSimpleDtoFrom)
                .collect(Collectors.toList());
    }
}



