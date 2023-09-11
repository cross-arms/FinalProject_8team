package com.techit.withus.web.comments.repository;

import com.techit.withus.web.comments.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentCommentRepository extends JpaRepository<Comments, Long> {
}
