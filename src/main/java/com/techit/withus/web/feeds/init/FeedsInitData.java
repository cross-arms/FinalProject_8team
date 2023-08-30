package com.techit.withus.web.feeds.init;

import com.techit.withus.web.feeds.domain.entity.FeedScope;
import com.techit.withus.web.feeds.domain.entity.FeedType;
import com.techit.withus.web.feeds.domain.entity.Feeds;
import com.techit.withus.web.feeds.repository.FeedRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;
import com.techit.withus.web.users.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FeedsInitData {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        List<Users> users = userRepository.findAll();

        Optional<Users> findUser = users.stream()
                .filter(u -> u.getRole() == Roles.ROLE_USER)
                .findFirst();

        if (findUser.isPresent()) {
            List<Feeds> feeds = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                feeds.add(Feeds.create(findUser.get(), i + " 타이틀", i + " 내용", i + " image.jpg", FeedType.NORMAL, FeedScope.PUBLIC));
            }

            feedRepository.saveAll(feeds);
        }
    }
}
