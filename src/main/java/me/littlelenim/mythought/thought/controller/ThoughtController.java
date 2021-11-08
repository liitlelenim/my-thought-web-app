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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/thoughts")
@RequiredArgsConstructor
public class ThoughtController {

    private final ThoughtService thoughtService;
    private final TagService tagService;
    private final CommentService commentService;

    /*
    w projekcie uzywasz security, co oznacza, że masz nadanych userów
    w takim razie warto przemyśleć sprawę zabezpieczenia enpointów i zacząć używać np adnotacji @PreAuthorize i limitować dostęp
     */

    @GetMapping("/pages/{pageNumber}")
    public List<ThoughtOverviewDto> getThoughtsPage(@PathVariable int pageNumber, @RequestParam(required = false) String tag
            , @RequestParam Optional<String> username, Pageable pageable) {

        /*
        1. nie przekazuje w kontrolerze optionali jako parametrów. Rozumiem, że chodziło Ci o to, że są one niewymagane, można to załatwić w ten sposób @RequestParam(required = false)
        2. w kontrolerze nie powinno być tak rozbudowanej logiki
        - jeśłi chcesz pobrać myśli po userze zrób do tego oddzielny enpoint a info o stronie przekaż jako obiekt pageable
        (czyli parametry w zapytaniu &page=0?size=20)
        wg konwencji REST powinno to być mniej więcej tak:

        taki kod jest ciężki do przetestowania i bardzo nieczytelny

        /api/thoughts
        /api/thoughts/users
        /api/thoughts/users/{userId}
        /api/thoughts/latest
        /api/thoughts/tsgs/{tag}


         */
        List<Thought> thoughts;

        if (tag.isPresent()) {
            thoughts = thoughtService.getLatestThoughtsPageByTag(pageNumber, tag.get());
        } else if (username.isPresent()) {
            thoughts = thoughtService.getLatestThoughtsPageByUser(pageNumber, username.get());
        } else {
            thoughts = thoughtService.getLatestThoughtsPage(pageNumber);
        }

        List<ThoughtOverviewDto> formattedThoughts = new ArrayList<>();
        thoughts.forEach((thought -> formattedThoughts.add(new ThoughtOverviewDto(thought.getId(),
                thought.getContent(),
                thought.getPostDate(),
                thoughtService.getLikersUsernames(thought.getId()),
                tagService.tagListToTagNameList(thought.getTags()),
                thoughtService.getAuthor(thought.getId()).getUsername()))));

        return formattedThoughts;
    }

    @GetMapping("/{id}")
    // to jest rónież dość skomplikowana logika, czy na pewno musisz zwracać tak rozbudowany obiekt?
    public FullThoughtDto getThought(@PathVariable long id) {
        Thought thought = thoughtService.getByIdWithCommentsAndLikes(id);
        List<GetCommentDto> formattedComments = new ArrayList<>();
        thought.getComments()
                .forEach(comment -> formattedComments.add(GetCommentDto.mapFrom(comment)));

        return new FullThoughtDto(id,
                thought.getContent(),
                thought.getPostDate(),
                tagService.tagListToTagNameList(thought.getTags()),
                thoughtService.getLikersUsernames(id), formattedComments,
                thoughtService.getAuthor(thought.getId()).getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteThought(@PathVariable long id, Principal principal) {
        thoughtService.delete(id, principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/likes")
    public void toggleLike(@PathVariable long id, Principal principal) {
        thoughtService.toggleLike(id, principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public void postComment(@RequestBody @Validated PostCommentDto dto, @PathVariable long id, Principal principal) {
        commentService.post(dto, id, principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postThought(@RequestBody @Validated PostThoughtDto dto, Principal principal) {
        thoughtService.post(dto, principal.getName());
    }
}
