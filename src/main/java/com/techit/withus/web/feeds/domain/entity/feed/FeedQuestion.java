package com.techit.withus.web.feeds.domain.entity.feed;

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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feeds feeds;

    private Long deposit; // 질문 피드의 예치금: ex) 3000, 5000

    @Enumerated(EnumType.STRING)
    private QuestionStatus status; // 질문 피드의 해결 상태: ex) 해결중, 해결완료

    private LocalDateTime questionDueDate;

    public static FeedQuestion createInit(Feeds feeds, Long deposit, LocalDateTime questionDueDate) {
        return FeedQuestion.builder()
                .feeds(feeds)
                .deposit(deposit)
                .status(QuestionStatus.IN_PROGRESS)
                .questionDueDate(questionDueDate)
                .build();
    }

    /**
     * 해당 enum 객체는 INIT 을 현재 사용하지 않습니다. -> client 의 구현 부담
     * 질문 피드를 등록하는 순간, 피드의 상태 값은 IN_PROGRESS 로 세팅합니다.
     * 질문 마감일자가 도래할 경우, DOWN 으로 변경합니다. -> spring batch or spring scheduler or api batch 등 다양한 방법이 존재합니다.
     * 클라이언트는 질문 마감일자를 년,월,일 만 입력 받고, 서버에서 23:59:59초로 세팅하여 저ㅏㅇ합니다.
     *
     */
    @Getter
    @AllArgsConstructor
    public enum QuestionStatus {
        INIT("현재는 사용 X"),
        IN_PROGRESS("질문 진행중(질문에 대한 답변을 받는중)"),
        DOWN("질문 마감(질문자의 질문 마감 일자에 도래했을 경우)"),
        ;

        private final String description;
    }

}
