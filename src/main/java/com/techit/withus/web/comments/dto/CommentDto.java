package com.techit.withus.web.comments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techit.withus.web.comments.domain.entity.ChildComments;
import com.techit.withus.web.comments.domain.entity.Comments;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.users.domain.dto.UserDto;
import com.techit.withus.web.users.domain.entity.Users;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.techit.withus.web.users.domain.dto.UserDto.*;

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
        private UserResponse writer;
        private String content;
        private Long parentCommentId;
        private List<CommentResponse> childCommentResponses;
        private Integer likeCount;
        private String deleteYn;

        public static List<CommentResponse> toDtoFrom(
            List<Comments> comments
        ) {
            // comment, child
            List<CommentResponse> list = new ArrayList<>();
            Map<Long, CommentResponse> map = new HashMap<>();
            comments.stream().forEach(comment -> {
                CommentResponse response = CommentResponse.toDto(comment);

                if (comment.getParentComment() != null) {
                    response.setParentId(comment.getParentComment().getCommentId());
                }

                map.put(response.getId(), response);

                if (comment.getParentComment() != null)
                    map.get(comment.getParentComment().getCommentId()).getChildCommentResponses().add(response);
                else list.add(response);
            });

            return list;
        }

        private void setParentId(Long commentId) {
            this.parentCommentId = commentId;
        }

        private static CommentResponse toDto(Comments comment) {
            return CommentResponse.builder()
                    .id(comment.getCommentId())
                    .writer(UserResponse.create(comment.getUsers()))
                    .content(comment.getContent())
                    .likeCount(comment.getLikeCount())
                    .deleteYn(comment.getDeleteYn())
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
        private UserResponse writer;
        private String content;

        public static ChildCommentResponse toDtoFrom(ChildComments childComments) {
            return ChildCommentResponse.builder()
                .id(childComments.getId())
                .writer(UserResponse.create(childComments.getUsers()))
                .content(childComments.getContent())
                .build();
        }
    }
}
