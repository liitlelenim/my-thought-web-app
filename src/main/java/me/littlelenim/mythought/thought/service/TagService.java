package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag findByStringOrCreate(String tagName) {
        Tag foundTag = tagRepository.findByName(tagName);
        if (foundTag != null) {
            return foundTag;
        } else {
            return save(new Tag(tagName));
        }
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
