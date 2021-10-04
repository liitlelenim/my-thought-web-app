package me.littlelenim.mythought.thought.controller;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.request.PostThoughtDto;
import me.littlelenim.mythought.thought.dto.response.ThoughtOverviewDto;
import me.littlelenim.mythought.thought.model.Thought;
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
            List<String> tagsNames = new ArrayList<>();
            thought.getTags().forEach(thoughtTag -> tagsNames.add(thoughtTag.getName()));
            formattedThoughts.add(new ThoughtOverviewDto(thought.getContent(), thought.getPostDate(), tagsNames));
        }));

        return formattedThoughts;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postThought(@RequestBody @Validated PostThoughtDto dto) {
        thoughtService.post(dto);
    }
}
