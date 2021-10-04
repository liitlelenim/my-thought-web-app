package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.request.PostCommentDto;
import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.CommentRepository;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ThoughtRepository thoughtRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThoughtService thoughtService;

    @AfterEach
    void tearDown() {
        thoughtRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void testPostingComment() {
        Thought thought = new Thought("Testing thought");
        Long thoughtId = thoughtService.save(thought).getId();
        final String commentText = "Test comment";
        PostCommentDto dto = new PostCommentDto(commentText);
        Comment postedComment = commentService.post(dto, thoughtId);

        assertEquals(thought, postedComment.getThought());
        assertEquals(commentText, postedComment.getContent());
        assertEquals(1, thoughtService.getByIdWithComments(thoughtId).getComments().size());
    }

}