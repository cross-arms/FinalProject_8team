package com.techit.withus.web.feeds.domain.entity.feed;

import com.techit.withus.web.feeds.domain.entity.category.Categories;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.List;

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

    private String imageURL;

    @Enumerated(EnumType.STRING)
    private FeedType type; // 일반 or 질문

    @Enumerated(EnumType.STRING)
    private FeedScope scope; // 노출 범위

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "created_date")
    private Timestamp createdDate;

    public static Feeds create(
            Users user, String title, String content,
            String imageURL, FeedType feedType, FeedScope feedScope,
            Categories category
    ) {
        return Feeds.builder()
                .writer(user)
                .type(feedType)
                .scope(feedScope)
                .title(title)
                .content(content)
                .imageURL(StringUtils.isBlank(imageURL) ? "" : imageURL)
                .category(category)
                .build();
    }

    public void setFeedQuestion(FeedQuestion feedsQuestionEntity) {
        this.feedQuestion = feedsQuestionEntity;
    }

    public void updateScopeToPublic() {
        this.scope = FeedScope.PUBLIC;
    }
}
