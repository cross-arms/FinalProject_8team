package com.techit.withus.web.feeds.domain.entity.feed;

import com.techit.withus.web.feeds.enumeration.QuestionStatus;
import com.techit.withus.web.users.domain.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feed_questions")
public class FeedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users chosenPerson;

    private String content;

    private Long deposit; // 질문 피드의 예치금: ex) 3000, 5000

    @Enumerated(EnumType.STRING)
    private QuestionStatus status; // 질문 피드의 해결 상태: ex) 해결중, 해결완료

    private LocalDateTime questionDueDate;

    public static FeedQuestion createInit(Feeds feeds, String content, Long deposit, LocalDateTime questionDueDate) {
        return FeedQuestion.builder()
                .feeds(feeds)
                .content(content)
                .deposit(deposit)
                .status(QuestionStatus.RESOLVING)
                .questionDueDate(questionDueDate)
                .build();
    }
}
