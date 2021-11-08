package me.littlelenim.mythought.thought.repository;

import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentsByThoughtOrderByPostDateDesc(Thought thought, Pageable pageable);
}
