package com.techit.withus.web.comments.repository;

import com.techit.withus.web.comments.domain.entity.ParentComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentCommentRepository extends JpaRepository<ParentComments, Long> {
}
