package me.littlelenim.mythought.thought.controller;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.request.PostCommentDto;
import me.littlelenim.mythought.thought.dto.request.PostThoughtDto;
import me.littlelenim.mythought.thought.dto.response.FullThoughtDto;
import me.littlelenim.mythought.thought.dto.response.GetCommentDto;
import me.littlelenim.mythought.thought.dto.response.ThoughtOverviewDto;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.service.CommentService;
import me.littlelenim.mythought.thought.service.TagService;
import me.littlelenim.mythought.thought.service.ThoughtService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/thoughts")
@CrossOrigin
@RequiredArgsConstructor
public class ThoughtController {

    private final ThoughtService thoughtService;
    private final TagService tagService;
    private final CommentService commentService;

    @GetMapping("/pages/{pageNumber}")
    public List<ThoughtOverviewDto> getThoughtsPage(@PathVariable int pageNumber, @RequestParam Optional<String> tag) {
        List<Thought> thoughts;

        if (tag.isPresent()) {
            thoughts = thoughtService.getLatestThoughtsPageByTag(pageNumber, tag.get());
        } else {
            thoughts = thoughtService.getLatestThoughtsPage(pageNumber);
        }

        List<ThoughtOverviewDto> formattedThoughts = new ArrayList<>();
        thoughts.forEach((thought -> {
            formattedThoughts.add(new ThoughtOverviewDto(thought.getContent(), thought.getPostDate(),
                    tagService.tagListToTagNameList(thought.getTags())));
        }));

        return formattedThoughts;
    }

    @GetMapping("/{id}")
    public FullThoughtDto getThought(@PathVariable long id) {
        Thought thought = thoughtService.getByIdWithComments(id);
        List<GetCommentDto> formattedComments = new ArrayList<>();
        thought.getComments().forEach(comment -> formattedComments.add(new GetCommentDto(comment.getContent(), comment.getPostDate())));

        return new FullThoughtDto(thought.getContent(), thought.getPostDate(),
                tagService.tagListToTagNameList(thought.getTags()), formattedComments);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public void postComment(@RequestBody @Validated PostCommentDto dto, @PathVariable long id) {
        commentService.post(dto, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postThought(@RequestBody @Validated PostThoughtDto dto) {
        thoughtService.post(dto);
    }
}
