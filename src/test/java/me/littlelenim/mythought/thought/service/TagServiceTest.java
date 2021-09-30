package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TagServiceTest {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

    @AfterEach
    void tearDown() {
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
}