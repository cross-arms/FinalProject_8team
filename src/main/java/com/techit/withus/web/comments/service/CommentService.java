package com.techit.withus.web.comments.service;

import com.techit.withus.web.comments.domain.entity.Comments;
import com.techit.withus.web.comments.dto.CommentDto.RegisterParentCommentRequest;
import com.techit.withus.web.comments.exception.CommentNotFoundException;
import com.techit.withus.web.comments.repository.CommentRepository;
import com.techit.withus.web.feeds.domain.dto.FeedsDto.FeedResponse;
import com.techit.withus.web.feeds.domain.entity.feed.Feeds;
import com.techit.withus.web.feeds.service.FeedService;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.techit.withus.web.comments.dto.CommentDto.ModifyParentCommentRequest;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserService userService;
    private final FeedService feedService;
    private final CommentRepository commentRepository;

    /**
     * 피드에 등록된 모든 댓글 조회
     *
     * @param feedId
     * @param pageRequest
     * @return
     */
    public Page<FeedResponse> getAllComments(Long feedId, Pageable pageRequest) {
        return commentRepository.findAllByFeeds(feedService.findFeed(feedId), pageRequest);
    }

    /**
     * 피드에 댓글, 대댓글 등록
     *
     * @param request
     * @return
     */
    @Transactional
    public void registerComment(RegisterParentCommentRequest request) {
        Users writer = userService.findUser(request.getUserId());

        Feeds feed = feedService.findFeed(request.getFeedId());

        // 대댓글의 경우
        if (request.hasParentComment()) {
            Comments parentComment = getParentComment(request.getParentCommentId());
            Comments reply = Comments.initReply(parentComment, writer, feed, request.getContent());
            saveComment(reply);

            return;
        }

        Comments comment = Comments.init(writer, feed, request.getContent());

        saveComment(comment);
    }

    private void saveComment(Comments reply) {
        commentRepository.save(reply);
    }

    private Comments getParentComment(Long parentCommentId) {
        return commentRepository.findById(parentCommentId).orElseThrow(
                () -> new CommentNotFoundException()
        );
    }

    @Transactional
    public void updateComment(ModifyParentCommentRequest request) {
        Feeds feed = feedService.findFeed(request.getFeedId());

        Comments findComment = findCommentByCommentId(feed, request.getCommentId());

        findComment.modifyContent(request.getContent());
    }

    private static Comments findCommentByCommentId(Feeds feed, Long commentId) {
        return feed.getComments().stream()
                .filter(comments -> comments.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException());
    }

    @Transactional
    public void deleteComment(Long commentId, Long feedId) {
        Feeds feed = feedService.findFeed(feedId);

        Comments findComment = findCommentByCommentId(feed, commentId);

        findComment.delete();
    }
}

