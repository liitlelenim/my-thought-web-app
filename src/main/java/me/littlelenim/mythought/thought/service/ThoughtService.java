package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.PostThoughtDto;
import me.littlelenim.mythought.thought.exception.InvalidThoughtIdException;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ThoughtService {
    private final ThoughtRepository thoughtRepository;
    private final TagService tagService;

    public Thought post(PostThoughtDto dto) {
        Thought thought = new Thought(dto.getContent());

        List<Tag> tags = new ArrayList<>();
        dto.getTags().forEach((tag) -> tags.add(tagService.findByStringOrCreate(tag)));
        thought.setTags(tags);
        return save(thought);
    }

    public Thought save(Thought thought) {
        return thoughtRepository.save(thought);
    }

    public List<Thought> getAll() {
        return thoughtRepository.findAll();
    }

    public Thought getById(Long id){
        return thoughtRepository.findById(id).orElseThrow(
                ()->new InvalidThoughtIdException("Could not find a thought with given id"));
    }

    public List<Thought> getLatestThoughtsPage(int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        return thoughtRepository.findByOrderByPostDateDesc(pageRequest);
    }
}
