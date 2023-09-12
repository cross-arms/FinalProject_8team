package com.techit.withus.web.comments.service;

import com.techit.withus.web.feeds.domain.dto.FeedsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    public Page<FeedsDto.FeedResponse> getAllComments(PageRequest of) {
        // TODO

        return null;
    }

    // 게시물에 댓글 달기



    // 댓글에 대닷글 달기
}
