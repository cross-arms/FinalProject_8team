package com.techit.withus.web.comments.service;

import com.techit.withus.web.comments.repository.ChildCommentRepository;
import com.techit.withus.web.comments.repository.ParentCommentRepository;
import com.techit.withus.web.feeds.domain.dto.FeedDto;
import com.techit.withus.web.feeds.service.FeedService;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final FeedService feedService;
    private final UserService userService;

    private final ChildCommentRepository childCommentRepository;
    private final ParentCommentRepository parentCommentRepository;

    public Page<FeedDto.FeedResponse> getAllComments(PageRequest of) {
        // TODO

        return null;
    }

    // 게시물에 댓글 달기



    // 댓글에 대닷글 달기
}
