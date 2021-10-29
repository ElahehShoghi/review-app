package com.spring.jpa.h2.review.repository;

import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import com.spring.jpa.h2.review.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByStatus(AcceptanceStatus status);
}
