package com.techit.withus.web.feeds.domain.entity.feed;

import com.techit.withus.web.comments.domain.entity.Comments;
import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.feeds.enumeration.FeedScope;
import com.techit.withus.web.feeds.enumeration.FeedType;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Builder.Default
    @OneToMany(mappedBy = "feeds", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Images> images = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "feeds")
    private List<Comments> comments = new ArrayList<>();

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
        return Feeds.builder()
                .writer(user)
                .type(feedType)
                .scope(feedScope)
                .title(title)
                .content(content)
                .images(CollectionUtils.isEmpty(images) ? EMPTY_LIST : images)
                .category(category)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public void setFeedQuestion(FeedQuestion feedsQuestionEntity) {
        this.feedQuestion = feedsQuestionEntity;
    }

    public void updateScopeToPublic() {
        this.scope = FeedScope.PUBLIC;
    }

    public void setImageList(List<Images> images) {
        this.images = images;

        for (Images image : images) {
            image.setFeeds(this);
        }
    }
}
