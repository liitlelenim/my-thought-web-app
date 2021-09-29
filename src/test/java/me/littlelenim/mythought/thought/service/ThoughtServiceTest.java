package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.PostThoughtDto;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
    void testThoughtPostingUsingDto() {
        PostThoughtDto dto = new PostThoughtDto("Testing", List.of("test_tag1", "test_tag2"));
        thoughtService.post(dto);
    }

    @Test
    void testGettingFirstPageOfThoughts(){
        IntStream.range(0,15).forEach((index)-> thoughtService.save(new Thought( "index:"+index)));

        List<Thought> thoughtList = thoughtService.getLatestThoughtsPage(0);
        assertFalse(thoughtList.isEmpty());

        Thought firstThoughtFromList = thoughtList.get(0);
        Thought secondThoughtFromList = thoughtList.get(1);

        assertTrue(firstThoughtFromList.getPostDate().after(secondThoughtFromList.getPostDate()));
    }
}