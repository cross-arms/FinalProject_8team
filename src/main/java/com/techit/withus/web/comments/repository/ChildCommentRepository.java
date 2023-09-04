package com.techit.withus.web.comments.repository;

import com.techit.withus.web.comments.domain.entity.ChildComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildCommentRepository extends JpaRepository<ChildComments, Long> {
}
