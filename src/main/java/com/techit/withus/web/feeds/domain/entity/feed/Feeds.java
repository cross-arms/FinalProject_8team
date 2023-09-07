package com.techit.withus.web.feeds.domain.entity.feed;

import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feeds")
public class Feeds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users writer;

    @OneToOne(mappedBy = "feeds")
    private FeedQuestion feedQuestion;

    @Lob
    private String title;

    private String content;

    @OneToMany(mappedBy = "feeds", fetch = FetchType.LAZY)
    private List<Images> images;

    @Enumerated(EnumType.STRING)
    private FeedType type; // 일반 or 질문

    @Enumerated(EnumType.STRING)
    private FeedScope scope; // 노출 범위

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public static Feeds create(
            Users user, String title, String content,
            List<Images> images, FeedType feedType, FeedScope feedScope,
            Categories category
    ) {
        Feeds feed = Feeds.builder()
                .writer(user)
                .type(feedType)
                .scope(feedScope)
                .title(title)
                .content(content)
                .images(CollectionUtils.isEmpty(images) ? EMPTY_LIST : images)
                .category(category)
                .createdDate(LocalDateTime.now())
                .build();

        images.forEach(images1 ->  {
            images1.setFeeds(feed);
        });

        return feed;
    }

    public void setFeedQuestion(FeedQuestion feedsQuestionEntity) {
        this.feedQuestion = feedsQuestionEntity;
    }

    public void updateScopeToPublic() {
        this.scope = FeedScope.PUBLIC;
    }
}
