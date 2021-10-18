package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.request.PostThoughtDto;
import me.littlelenim.mythought.thought.exception.InvalidThoughtIdException;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.ThoughtRepository;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.service.AppUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ThoughtService {
    private final ThoughtRepository thoughtRepository;
    private final TagService tagService;
    private final AppUserService appUserService;

    @Transactional
    public Thought post(PostThoughtDto dto, String username) {
        Thought thought = new Thought(dto.getContent());
        AppUser appUser = appUserService.findByUsername(username);
        appUser.addThought(thought);
        List<Tag> tags = new ArrayList<>();
        dto.getTags().forEach((tag) -> tags.add(tagService.findByStringOrCreate(tag)));
        thought.setTags(tags);
        return save(thought);
    }

    @Transactional
    public Thought save(Thought thought) {
        return thoughtRepository.save(thought);
    }

    public List<Thought> getAll() {
        return thoughtRepository.findAll();
    }

    public Thought getById(Long id) {
        return thoughtRepository.findById(id).orElseThrow(
                () -> new InvalidThoughtIdException("Could not find a thought with given id"));
    }

    public Thought getByIdWithCommentsAndLikes(Long id) {
        return thoughtRepository.findByIdAndJoinCommentsAndLikes(id).orElseThrow(
                () -> new InvalidThoughtIdException("Could not find a thought with given id"));
    }

    public List<Thought> getLatestThoughtsPage(int page) {
        Pageable pageRequest = PageRequest.of(page, 5);
        return thoughtRepository.findByOrderByPostDateDesc(pageRequest);
    }

    public List<Thought> getLatestThoughtsPageByTag(int page, String tagName) {
        Tag tag = tagService.findByStringOrCreate(tagName);
        Pageable pageRequest = PageRequest.of(page, 5);
        return thoughtRepository.findByTagOrderByPostDateDesc(pageRequest, tag);
    }

    public List<Thought> getLatestThoughtsPageByUser(int page, String username) {
        Pageable pageRequest = PageRequest.of(page, 5);
        return thoughtRepository.findByUsernameOrderByPostDateDesc(pageRequest, username);
    }

    @Transactional
    public void delete(Long thoughtId, String username) {
        Thought thought = getById(thoughtId);
        if (thought.getAuthor().getUsername().equals(username)) {
            AppUser user = appUserService.findByUsername(username);
            user.removeThought(thought);
            thoughtRepository.delete(thought);
        }
    }

    @Transactional
    public void toggleLike(Long thoughtId, String username) {
        AppUser user = appUserService.findByUsername(username);
        Thought thought = thoughtRepository.findByIdAndJoinLikes(thoughtId).orElseThrow(
                () -> new InvalidThoughtIdException("Could not find a thought with given id"));
        if (thought.getLikedBy().contains(user)) {
            thought.removeLike(user);
        } else {
            thought.addLike(user);
        }
        save(thought);
    }

    public List<String> getLikersUsernames(Long thoughtId) {
        return thoughtRepository.findByIdAndJoinLikes(thoughtId).orElseThrow(
                () -> new InvalidThoughtIdException("Could not find a thought with given id"))
                .getLikedBy().stream().map(AppUser::getUsername).collect(Collectors.toList());
    }

    public AppUser getAuthor(Long id) {
        return thoughtRepository.getThoughtWithAuthor(getById(id)).getAuthor();
    }

}
