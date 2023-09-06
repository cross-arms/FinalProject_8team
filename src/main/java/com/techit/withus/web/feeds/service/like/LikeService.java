package com.techit.withus.web.feeds.service.like;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.domain.entity.like.Likes;
import com.techit.withus.web.feeds.dto.like.LikeDto.RegisterLikeRequest;
import com.techit.withus.web.feeds.repository.like.LikeRepository;
import com.techit.withus.web.feeds.service.feed.FeedService;
import com.techit.withus.web.users.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    private final UserService userService;
    private final FeedService feedService;
    private final LikeRepository likeRepository;

    public void saveLike(RegisterLikeRequest request) {
        Feeds feeds = feedService.getFeedBy(request.getFeedId());

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
