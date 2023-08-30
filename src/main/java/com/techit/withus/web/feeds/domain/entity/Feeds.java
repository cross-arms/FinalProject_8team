package com.techit.withus.web.feeds.domain.entity;

import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feeds")
public class Feeds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users writer;

    @OneToOne(mappedBy = "feeds")
    private FeedQuestion feedQuestion;

    @Lob
    private String title;

    private String content;

    private String imageURL;

    @Enumerated(EnumType.STRING)
    private FeedType type; // 일반 or 질문

    @Enumerated(EnumType.STRING)
    private FeedScope scope; // 노출 범위

    public static Feeds create(
            Users user, String title, String content, String imageURL, FeedType feedType, FeedScope feedScope
    ) {
        return Feeds.builder()
                .writer(user)
                .type(feedType)
                .scope(feedScope)
                .title(title)
                .content(content)
                .imageURL(StringUtils.isBlank(imageURL) ? "" : imageURL)
                .build();
    }

    public void setFeedQuestion(FeedQuestion feedsQuestionEntity) {
        this.feedQuestion = feedsQuestionEntity;
    }

    public void updateScopeToPublic() {
        this.scope = FeedScope.PUBLIC;
    }
}
