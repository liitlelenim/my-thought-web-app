package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.dto.request.PostThoughtDto;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.repository.TagRepository;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private ThoughtService thoughtService;
    @Autowired
    private ThoughtRepository thoughtRepository;

    @AfterEach
    void tearDown() {
        thoughtRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    void testFindOrCreateMethod() {
        final String tag_name = "testing_tag";
        assertTrue(tagRepository.findAll().isEmpty());

        Tag tag = tagService.findByStringOrCreate(tag_name);
        assertEquals(tag_name, tag.getName());
        assertEquals(1, tagRepository.findAll().size());

        Tag foundTag = tagService.findByStringOrCreate(tag_name);
        assertEquals(tag, foundTag);
        assertEquals(1, tagRepository.findAll().size());
    }

    @Test
    void testGettingMostPopularTags() {
        final String mostPopularTag = "tag_1";
        PostThoughtDto postThoughtDto = new PostThoughtDto("Testing", List.of(mostPopularTag));
        thoughtService.post(postThoughtDto);
        PostThoughtDto secondPostThoughtDto = new PostThoughtDto("Testing2", List.of("tag_2", mostPopularTag));
        thoughtService.post(secondPostThoughtDto);
        List<Tag> mostPopularTags = tagService.getMostPopularTags();

        assertFalse(mostPopularTags.isEmpty());
        assertEquals(2, mostPopularTags.size());
        assertEquals(mostPopularTag, mostPopularTags.get(0).getName());
    }
}