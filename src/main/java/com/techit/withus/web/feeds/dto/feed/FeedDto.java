package com.techit.withus.web.feeds.dto.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techit.withus.common.exception.InvalidValueException;
import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion;
import com.techit.withus.web.feeds.domain.entity.feed.FeedQuestion.QuestionStatus;
import com.techit.withus.web.feeds.domain.entity.feed.FeedScope;
import com.techit.withus.web.feeds.domain.entity.feed.FeedType;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.dto.UserDto.UserResponse;
import com.techit.withus.web.users.domain.entity.Users;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.techit.withus.common.exception.ErrorCode.INVALID_INPUT_VALUE;
import static com.techit.withus.web.feeds.domain.entity.feed.FeedType.QUESTION;

/**
 * feedDto 객체는 데이터 전달을 목적으로 하는 객체입니다.
 * 가독성을 위해 feedDto 안에는 client로 부터 전달 받고 전달 해주는 request, response 객체를 갖고 있습니다.
 */
public class FeedDto {

    /** request **/
    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterFeedRequest {

        private Long userId;
        private String title;
        private String content;
        private String imageURL;
        private FeedType feedType;
        private FeedScope feedScope;
        private Long deposit;
        private LocalDate questionDueDate; // 질문 마감일자

        public Feeds toFeedsEntity(Users user) {
            return Feeds.create(user, title, content, imageURL, feedType, feedScope);
        }

        public boolean isQuestionFeed() {
            return feedType == QUESTION;
        }

        public FeedQuestion toFeedsQuestionEntity(Feeds feeds) {
            return FeedQuestion.createInit(feeds, this.deposit, toLocalDateTime(this.questionDueDate));
        }

        public LocalDateTime toLocalDateTime(LocalDate localDate) {
            return localDate.atTime(LocalTime.of(23, 59, 59));
        }

        public void questionFeedValidation() {
            LocalDateTime localDateTime = toLocalDateTime(questionDueDate);
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(localDateTime)) {
                throw new InvalidValueException(INVALID_INPUT_VALUE);
            }
        }
    }

    /** response **/
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class FeedMainResponse {

        private Page<FeedResponse> feeds;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class FeedResponse {

        private Long id;
        private UserResponse writer;
        private FeedQuestionResponse feedQuestion;
        private String title;
        private String content;
        private String imageURL;
        private FeedType type;
        private FeedScope scope;

        public static FeedResponse toDtoFrom(Feeds feed) {
            return FeedResponse.builder()
                .id(feed.getId())
                .writer(UserResponse.create(feed.getWriter()))
                .feedQuestion(FeedQuestionResponse.create(feed.getFeedQuestion()))
                .title(feed.getTitle())
                .content(feed.getContent())
                .imageURL(StringUtils.isBlank(feed.getImageURL()) ? null : feed.getImageURL())
                .type(feed.getType())
                .scope(feed.getScope())
                .build();
        }
    }


    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class FeedQuestionResponse {

        private Long id;
        private Long deposit;
        private QuestionStatus status;
        private LocalDateTime questionDueDate;

        public static FeedQuestionResponse create(FeedQuestion feedQuestion) {
            if (feedQuestion == null) {
                return new FeedQuestionResponse();
            }

            return FeedQuestionResponse.builder()
                .id(feedQuestion.getId())
                .deposit(feedQuestion.getDeposit())
                .status(feedQuestion.getStatus())
                .questionDueDate(feedQuestion.getQuestionDueDate())
                .build();
        }
    }
}