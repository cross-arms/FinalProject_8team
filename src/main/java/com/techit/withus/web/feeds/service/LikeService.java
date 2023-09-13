package com.techit.withus.web.feeds.service;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.feeds.domain.dto.LikeDto.RegisterLikeRequest;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.domain.entity.like.Likes;
import com.techit.withus.web.feeds.exception.FeedLikeException;
import com.techit.withus.web.feeds.repository.like.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.techit.withus.common.exception.ErrorCode.ALREADY_LIKE;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    private final FeedService feedService;
    private final LikeRepository likeRepository;

    public void saveLike(RegisterLikeRequest request) {
        Feeds feeds = feedService.getFeedBy(request.getFeedId());

        Optional<Likes> findFeedLikes = likeRepository.findByFeedAndUser(request.getFeedId(), request.getUserId());

        if (findFeedLikes.isPresent() && findFeedLikes.get().isAlreadyLike()) {
            throw new FeedLikeException(ALREADY_LIKE);
        }

        likeRepository.save(Likes.createLike(feeds));
    }

    @Transactional
    public void cancelLike(RegisterLikeRequest request) {
        // 사용자가 좋아요한 피드 정보가 존재하지 않을 경우
        Likes likes = likeRepository.findByFeedAndUser(request.getFeedId(), request.getUserId()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND)
        );

        // 사실 연속 요청이 들어올 경우를 대비해서 예외처리를 해야 함
        likes.toUnlike();
    }
}
