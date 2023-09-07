package com.techit.withus.web.feeds.init;

import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.domain.entity.feed.FeedScope;
import com.techit.withus.web.feeds.domain.entity.feed.FeedType;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.repository.category.CategoryRepository;
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

    private final SignUpService signUpService;
    private final CategoryRepository categoryRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        Users user = saveUser();

        List<Categories> categories = saveCategories();

        List<Feeds> feeds = new ArrayList<>();
        for (int categoryId = 0; categoryId < categories.size(); categoryId++) {
            Categories category = categories.get(categoryId);
            FeedType feedType = FeedType.NORMAL;
            FeedScope feedScope = FeedScope.PUBLIC;

            for (int i = categoryId * 3; i < (categoryId + 1) * 3; i++) {
                feeds.add(
                        Feeds.create(
                                user,
                                i + " 타이틀",
                                i + " 내용",
                                new ArrayList<>(),
                                feedType,
                                feedScope,
                                category
                        )
                );
            }
        }

        feedRepository.saveAll(feeds);
    }

    private List<Categories> saveCategories() {
        Categories app = createInitCategory("웹");
        Categories web = createInitCategory("앱");
        Categories db = createInitCategory("DB");
        Categories javaScript = createInitCategory("JavaScript");
        Categories java = createInitCategory("Java");
        Categories react = createInitCategory("React");
        Categories vue = createInitCategory("Vue");
        Categories thymeleaf = createInitCategory("Thymeleaf");

        categoryRepository.saveAll(List.of(app, web, db, javaScript, java, react, vue, thymeleaf));

        return categoryRepository.findAll();
    }

    private static Categories createInitCategory(String name) {
        return Categories.builder()
                .name(name)
                .build();
    }


    private Users saveUser() {
        String email = "test12312312@gmail.com";
        signUpService.signUp(SignUpDTO.builder()
            .email(email)
            .password("1q2w3e3er4r5t")
            .build());

        return userRepository.findByEmail(email).get();
    }
}
