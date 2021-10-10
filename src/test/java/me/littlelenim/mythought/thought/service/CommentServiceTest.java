package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.request.PostCommentDto;
import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.CommentRepository;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.repository.AppUserRepository;
import me.littlelenim.mythought.user.service.AppUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "secret=secret")
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ThoughtRepository thoughtRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThoughtService thoughtService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserRepository appUserRepository;

    private final String testUsername = "test_user";

    @BeforeEach
    void setUp() {
        appUserService.save(new AppUser(testUsername, testUsername));
    }

    @AfterEach
    void tearDown() {
        thoughtRepository.deleteAll();
        commentRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void testPostingComment() {
        Thought thought = new Thought("Testing thought");
        AppUser user = appUserService.findByUsernameWithThoughts(testUsername);
        user.addThought(thought);
        Long thoughtId = thoughtService.save(thought).getId();
        final String commentText = "Test comment";
        PostCommentDto dto = new PostCommentDto(commentText);
        Comment postedComment = commentService.post(dto, thoughtId, testUsername);

        assertEquals(thought, postedComment.getThought());
        assertEquals(commentText, postedComment.getContent());
        assertEquals(1, thoughtService.getByIdWithComments(thoughtId).getComments().size());
    }

}