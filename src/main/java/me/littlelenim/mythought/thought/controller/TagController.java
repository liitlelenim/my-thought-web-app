package me.littlelenim.mythought.thought.controller;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.model.Tag;
import me.littlelenim.mythought.thought.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/most-popular")
    public List<String> getMostPopularTags() {
        return tagService.getMostPopularTags().stream().map(Tag::getName).collect(Collectors.toList());
        //to tagservice powinien zwrócić posortowaną listę samych tagów, kontroler nie powinien tego robić
        // później możesz chcieć cache'ować wyniki i odpytywać bazę o nie np co minutę, a nie przy każdym strzale do kontrolera
        // dzięki temu aplikacja ma mniej roboty, mniej obiektów do tworzenia
    }
}
