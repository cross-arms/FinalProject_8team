package com.techit.withus.web.comments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techit.withus.web.comments.domain.entity.ChildComments;
import com.techit.withus.web.comments.domain.entity.Comments;
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
        private Long parentCommentId;

        public boolean hasParentComment() {
            return parentCommentId != null && parentCommentId != 0L;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class ModifyParentCommentRequest {

        private Long feedId;
        private Long commentId;
        private String content;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class DeleteParentCommentRequest {

        private Long feedId;
        private Long commentId;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterChildCommentRequest {
        private Long userId;
        private Long feedId;
        private Long parentCommentId;
        private String content;

        public ChildComments toCommentsEntity(Users user, Feeds feed, Comments comments) {
            return ChildComments.create(user, feed, comments, content);
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
            Comments comments,
            List<ChildComments> childCommentsList
        ) {
            return CommentResponse.builder()
                .id(comments.getCommentId())
                .writer(UserDto.UserResponse.create(comments.getUsers()))
                .content(comments.getContent())
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
                .id(childComments.getId())
                .writer(UserDto.UserResponse.create(childComments.getUsers()))
                .content(childComments.getContent())
                .build();
        }
    }
}
