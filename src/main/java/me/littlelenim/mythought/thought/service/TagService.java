package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.exception.MaxTagLengthExceededException;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.repository.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag findByStringOrCreate(String tagName) {
        if (tagName.length() > 20) {
            throw new MaxTagLengthExceededException("Tag length should be at most 20 characters");
        }
        Tag foundTag = tagRepository.findByName(tagName);
        if (foundTag != null) {
            return foundTag;
        } else {
            return save(new Tag(tagName));
        }
    }


    public List<Tag> getMostPopularTags() {
        return tagRepository.getTagsOrderedByThoughtsAmount(PageRequest.of(0, 5));
    }

    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<String> tagListToTagNameList(List<Tag> tags) {
        List<String> tagNames = new ArrayList<>();
        tags.forEach(tag -> tagNames.add(tag.getName()));
        return tagNames;
    }
}
