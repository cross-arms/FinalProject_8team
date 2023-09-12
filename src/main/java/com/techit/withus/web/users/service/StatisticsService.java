package com.techit.withus.web.users.service;

import com.techit.withus.web.feeds.repository.feed.FeedQuestionRepository;
import com.techit.withus.web.feeds.repository.feed.FeedRepository;
import com.techit.withus.web.users.domain.dto.StatisticsDTO;
import com.techit.withus.web.users.repository.FollowRepository;
import com.techit.withus.web.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService
{
    private final FeedRepository feedRepository;
    private final FeedQuestionRepository feedQuestionRepository;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /**
     * 사용자가 작성한 피드의 개수,
     * 사용자가 채택된 댓글의 개수,
     * 사용자의 팔로워 수,
     * 사용자의 팔로우 수,
     */
    public StatisticsDTO getUserStatistics(Long userId)
    {
        return StatisticsDTO
                .builder()
                .feedsQuantity(this.getFeedsQuantity(userId))
                .chosenCommentsQuantity(this.getChosenCommentsQuantity(userId))
                .followerQuantity(this.getFollowerQuantity(userId))
                .followQuantity(this.getFollowQuantity(userId))
                .build();
    }

    public Long getFeedsQuantity(Long userId)
    {
        return feedRepository.countAllByWriter(
                userRepository.getReferenceById(userId)
        );
    }

    public Long getChosenCommentsQuantity(Long userId)
    {
        return feedQuestionRepository.countAllByChosenPerson(
                userRepository.getReferenceById(userId)
        );
    }

    public Long getFollowQuantity(Long userId)
    {
        return followRepository.countAllByWhoFollow(
                userRepository.getReferenceById(userId)
        );
    }

    public Long getFollowerQuantity(Long userId)
    {
        return followRepository.countAllByFollowWho(
                userRepository.getReferenceById(userId)
        );
    }


}
