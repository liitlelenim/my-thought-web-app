package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.TagRepository;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ThoughtService {
    private final ThoughtRepository thoughtRepository;

    public Thought save(Thought thought){
        return thoughtRepository.save(thought);
    }
    public List<Thought> getAll(){
        return thoughtRepository.findAll();
    }
    public List<Thought> getLatestThoughtsPage(int page){
        Pageable pageRequest = PageRequest.of(page,5);
        return thoughtRepository.findByOrderByPostDateDesc(pageRequest);
    }
}
