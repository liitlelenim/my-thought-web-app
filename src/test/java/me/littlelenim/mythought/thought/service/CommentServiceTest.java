package me.littlelenim.mythought.thought.service;

import me.littlelenim.mythought.thought.model.Comment;
import me.littlelenim.mythought.thought.model.Thought;
import me.littlelenim.mythought.thought.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {


    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    /*
    w takim teście testujemy sam serwis, w oderwaniu od repozytorium, dlatego jest ono zamockowane. Takie testy są szybkie.
    W teście poniżej zakładamy, że repo zwraca pożądany obiekt. Testujemy tylko, czy serwis zrobi z tym obiektem to, co powinien.
    W innym teście możemy zwrócić pusty obiekt, albo rzucić wyjątek i przetestować zachowanie serwisu w takim przypadku

    Dobrą praktyką jest pisanie testów do większośći klas, szczególnie seriwsów, mapperów itp
     */

    @Test
    void should_return_posts() { //nazwy bywają różne, zalezy od przyjętej konwencji

        //fajnie zorganizować sobie metody w testach w mouły given when then. Zwiększa to czytelność

        //GIVEN
        Comment comment = mock(Comment.class);
        List<Comment> comments = List.of(comment);
        Page<Comment> pageOfComments = mock(Page.class);
        doReturn(comments).when(pageOfComments).getContent();
        doReturn("author").when(comment).getAuthor();

        doReturn(pageOfComments).when(commentRepository).findCommentsByThoughtOrderByPostDateDesc(any(Thought.class), any(Pageable.class));

        //i tak dalej

        //WHEN

        //THEN
    }

}
