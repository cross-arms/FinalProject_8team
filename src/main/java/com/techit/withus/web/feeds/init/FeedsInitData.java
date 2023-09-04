package com.techit.withus.web.feeds.init;

import com.techit.withus.web.feeds.domain.entity.feed.FeedScope;
import com.techit.withus.web.feeds.domain.entity.feed.FeedType;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.repository.feed.FeedRepository;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.repository.UserRepository;
import com.techit.withus.web.users.service.SignUpService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FeedsInitData {

    private final FeedRepository feedRepository;
    private final SignUpService signUpService;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        String email = "test12312312@gmail.com";
        signUpService.signUp(SignUpDTO.builder()
            .email(email)
            .password("1q2w3e3er4r5t")
            .build());

        Users user = userRepository.findByEmail(email).get();

        List<Feeds> feeds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            feeds.add(Feeds.create(user, i + " 타이틀", i + " 내용", i + " image.jpg", FeedType.NORMAL, FeedScope.PUBLIC));
        }

        feedRepository.saveAll(feeds);
    }
}
