package me.littlelenim.mythought.thought.service;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.thought.dto.request.PostCommentDto;
import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.CommentRepository;
import me.littlelenim.mythought.user.model.AppUser;
import me.littlelenim.mythought.user.service.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ThoughtService thoughtService;
    private final AppUserService appUserService;

    //transactional nie jest potrzebny
    public Comment post(PostCommentDto dto, long thoughtId, String username) {

        AppUser user = appUserService.findByUsername(username);
        Thought thought = thoughtService.getById(thoughtId);
        Comment comment = new Comment(dto.getContent());
        thought.addComment(comment);
        user.addComment(comment);
        return save(comment);
    }

    public Page<Comment> getLatestCommentsPageByThought(Thought thought, Pageable pageable) { //nazwa metody sugeruje, że zwracasz pbiekt page, tymczasem jest to lista.
        //dobrze wrócić Page<Comment>. Z kontrollera możesz przekazać obiekt pageable, który ma w sobie info o numerze strony i ilości rekordów, które chcesz zwrócić
//        Pageable pageRequest = PageRequest.of(page, 5);
        //do powyższego -> jeśli przekazujesz jakiś argument w metodzie dobrze go użyć. Wstawianie wartości na sztywno w tym miejscu nie jest dobrą praktyką
        return commentRepository.findCommentsByThoughtOrderByPostDateDesc(thought, pageable); // dobrze byłoby nie zwracać encji prosto z bazy do kontrollera, tylko użyć mapowania na jakiś obiekt dto
        // w encji można dodać metodę statyczną mapFrom
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
