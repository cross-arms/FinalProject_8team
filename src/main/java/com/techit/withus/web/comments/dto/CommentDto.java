package com.techit.withus.web.comments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techit.withus.web.comments.domain.entity.ChildComments;
import com.techit.withus.web.comments.domain.entity.ParentComments;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.dto.UserDto;
import com.techit.withus.web.users.domain.entity.Users;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class CommentDto {

    /** request **/
    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterParentCommentRequest {
        private Long userId;
        private Long feedId;
        private String content;

        public ParentComments toCommentsEntity(Users user, Feeds feed) {
            return ParentComments.create(user, feed, content);
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterChildCommentRequest {
        private Long userId;
        private Long feedId;
        private Long parentCommentId;
        private String content;

        public ChildComments toCommentsEntity(Users user, Feeds feed, ParentComments parentComments) {
            return ChildComments.create(user, feed, parentComments, content);
        }
    }


    /** response **/
    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class CommentResponse {

        private Long id;
        private UserDto.UserResponse writer;
        private String content;
        private List<ChildCommentResponse> childCommentResponses;

        public static CommentResponse toDtoFrom(
            ParentComments parentComments,
            List<ChildComments> childCommentsList
        ) {
            return CommentResponse.builder()
                .id(parentComments.getPCommentId())
                .writer(UserDto.UserResponse.create(parentComments.getUsers()))
                .content(parentComments.getContent())
                .childCommentResponses(
                    childCommentsList.stream()
                        .map(ChildCommentResponse::toDtoFrom)
                        .collect(Collectors.toList())
                )
                .build();
        }
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class ChildCommentResponse {

        private Long id;
        private UserDto.UserResponse writer;
        private String content;

        public static ChildCommentResponse toDtoFrom(ChildComments childComments) {
            return ChildCommentResponse.builder()
                .id(childComments.getCCommentId())
                .writer(UserDto.UserResponse.create(childComments.getUsers()))
                .content(childComments.getContent())
                .build();
        }
    }
}
