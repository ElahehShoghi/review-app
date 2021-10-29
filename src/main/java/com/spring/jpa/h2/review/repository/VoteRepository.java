package com.spring.jpa.h2.review.repository;

import com.spring.jpa.h2.review.constant.AcceptanceStatus;
import com.spring.jpa.h2.review.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findVotesByStatus(AcceptanceStatus status);
}
