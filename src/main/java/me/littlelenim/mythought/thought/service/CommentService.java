package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.request.PostCommentDto;
import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ThoughtService thoughtService;

    @Transactional
    public Comment post(PostCommentDto dto) {
        Thought thought = thoughtService.getById(dto.getId());
        Comment comment = new Comment(dto.getContent());
        thought.addComment(comment);
        return save(comment);
    }

    public List<Comment> getLatestCommentsPageByThought(Thought thought, int page) {
        Pageable pageRequest = PageRequest.of(0, 5);
        return commentRepository.findCommentsByThoughtOrderByPostDateDesc(thought, pageRequest);
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
