package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.request.PostThoughtDto;
import me.littlelenim.mythought.thought.exception.InvalidThoughtIdException;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.TagRepository;
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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "secret=secret")
class ThoughtServiceTest {

    @Autowired
    private ThoughtRepository thoughtRepository;
    @Autowired
    private ThoughtService thoughtService;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserRepository appUserRepository;

    private String testUsername = "test_user";

    @BeforeEach
    void setUp() {
        appUserService.save(new AppUser(testUsername, testUsername));
    }

    @AfterEach
    void tearDown() {
        thoughtRepository.deleteAll();
        tagRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void testGettingAllThoughts() {
        assertTrue(thoughtService.getAll().isEmpty());
        AppUser appUser = appUserService.findByUsernameWithThoughts(testUsername);
        Thought thought = new Thought("Testing");
        appUser.addThought(thought);
        thoughtService.save(thought);
        assertEquals(1, thoughtService.getAll().size());
    }

    @Test
    void testGettingThoughtById() {

        assertThrows(InvalidThoughtIdException.class, () -> thoughtService.getById(0L));
        AppUser user = appUserService.findByUsernameWithThoughts(testUsername);
        Thought thought = new Thought("Testing");
        user.addThought(thought);
        Thought savedThought = thoughtService.save(thought);
        thoughtService.getById(savedThought.getId());
    }

    @Test
    void testThoughtPostingUsingDto() {
        PostThoughtDto dto = new PostThoughtDto("Testing", List.of("test_tag1", "test_tag2"));
        thoughtService.post(dto, testUsername);
    }

    @Test
    void testGettingFirstPageOfThoughts() {
        AppUser user = appUserService.findByUsernameWithThoughts(testUsername);
        IntStream.range(0, 15).forEach((index) -> {
            Thought thought = new Thought("index:" + index);
            user.addThought(thought);
            thoughtService.save(thought);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        List<Thought> thoughtList = thoughtService.getLatestThoughtsPage(0);
        assertFalse(thoughtList.isEmpty());

        Thought firstThoughtFromList = thoughtList.get(0);
        Thought secondThoughtFromList = thoughtList.get(1);

        assertTrue(firstThoughtFromList.getPostDate().after(secondThoughtFromList.getPostDate()));
    }

    @Test
    void testGettingFirstPageOfThoughtsWithGivenTag() {
        final String tagName = "tag";
        IntStream.range(0, 15).forEach((index) -> {
            thoughtService.post(new PostThoughtDto("index:" + index, List.of("tag")), testUsername);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        List<Thought> thoughtList = thoughtService.getLatestThoughtsPageByTag(0, tagName);
        assertFalse(thoughtList.isEmpty());
        assertEquals(tagName, thoughtList.get(0).getTags().get(0).getName());
    }

    @Test
    void testGettingFirstPageOfUserThoughts() {
        IntStream.range(0, 3).forEach((index) -> {
            thoughtService.post(new PostThoughtDto("index:" + index, List.of("tag")), testUsername);
        });
        appUserService.save(new AppUser("AnotherUser", "Password1"));
        IntStream.range(0, 3).forEach((index) -> {
            thoughtService.post(new PostThoughtDto("index:" + index, List.of("tag")), "AnotherUser");
        });
        List<Thought> thoughts = thoughtService.getLatestThoughtsPageByUser(0, testUsername);
        assertEquals(3, thoughts.size());
    }

    @Test
    void testPostingThought() {
        final String thoughtContent = "test";
        PostThoughtDto dto = new PostThoughtDto(thoughtContent, List.of("tag1", "tag2", "tag3"));

        Thought thought = thoughtService.post(dto, testUsername);
        assertEquals(thoughtContent, thought.getContent());
        assertFalse(thought.getTags().isEmpty());
    }

    @Test
    void testTogglingThoughtLike() {
        PostThoughtDto dto = new PostThoughtDto("Test", List.of("tag1", "tag2", "tag3"));
        Thought thought = thoughtService.post(dto, testUsername);
        Long thoughtId = thought.getId();

        assertEquals(0, thoughtService.getAmountOfLikes(thoughtId));
        thoughtService.toggleLike(thoughtId, testUsername);
        assertEquals(1, thoughtService.getAmountOfLikes(thoughtId));
        thoughtService.toggleLike(thoughtId, testUsername);
        assertEquals(0, thoughtService.getAmountOfLikes(thoughtId));
    }

    @Test
    void deleteThought() {
        IntStream.range(0, 3).forEach((index) -> {
            thoughtService.post(new PostThoughtDto("index:" + index, List.of("tag")), testUsername);
        });
        Long thoughtId = thoughtService.getAll().get(0).getId();
        thoughtService.delete(thoughtId, "not_existing");
        assertEquals(3, thoughtService.getAll().size());
        thoughtService.delete(thoughtId, testUsername);
        assertEquals(2, thoughtService.getAll().size());
    }

}