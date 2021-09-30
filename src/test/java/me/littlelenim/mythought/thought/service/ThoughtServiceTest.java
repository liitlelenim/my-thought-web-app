package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.PostThoughtDto;
import me.littlelenim.mythought.thought.exception.InvalidThoughtIdException;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThoughtServiceTest {

    @Autowired
    private ThoughtRepository thoughtRepository;
    @Autowired
    private ThoughtService thoughtService;

    @AfterEach
    void tearDown() {
        thoughtRepository.deleteAll();
    }

    @Test
    void testThoughtSaving() {
        Thought thought = new Thought("Testing");
        assertEquals(thought, thoughtService.save(thought));
    }

    @Test
    void testGettingAllThoughts() {
        assertTrue(thoughtService.getAll().isEmpty());

        thoughtService.save(new Thought("Testing"));
        assertEquals(1, thoughtService.getAll().size());
    }

    @Test
    void testGettingThoughtById() {

        assertThrows(InvalidThoughtIdException.class, () -> thoughtService.getById(0L));
        Thought thought = thoughtService.save(new Thought("Testing"));
        thoughtService.getById(thought.getId());


    }

    @Test
    void testThoughtPostingUsingDto() {
        PostThoughtDto dto = new PostThoughtDto("Testing", List.of("test_tag1", "test_tag2"));
        thoughtService.post(dto);
    }

    @Test
    void testGettingFirstPageOfThoughts() {
        IntStream.range(0, 15).forEach((index) -> {
            thoughtService.save(new Thought("index:" + index));
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
    void testPostingThought() {
        final String thoughtContent = "test";
        PostThoughtDto dto = new PostThoughtDto(thoughtContent, List.of("tag1", "tag2", "tag3"));

        Thought thought = thoughtService.post(dto);
        assertEquals(thoughtContent, thought.getContent());
        assertFalse(thought.getTags().isEmpty());
    }


}