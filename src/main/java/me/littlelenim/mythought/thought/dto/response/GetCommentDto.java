package me.littlelenim.mythought.thought.dto.response;

import lombok.Builder;
import lombok.Data;
import me.littlelenim.mythought.thought.model.Comment;

import java.util.Date;

@Data
@Builder //pozwala tworzyć bardzo użyteczne buildery to obiektów, zobacz na metodę mapForm w klasie Comment
public class GetCommentDto { // nazwa jest trochę myląca, może commentResponse?
    private final String authorUsername;
    private final String content;
    private final Date postDate; // date to stara, kłopotliwa klasa, lepiej przesiąść się na LocalDate / LocalDateTime, poczytaj o czasie javie
    //np tu https://stackoverflow.com/questions/35625501/java-api-and-client-app-how-to-handle-timezone-properly/35635242

    public static GetCommentDto mapFrom(Comment comment) {
        return GetCommentDto.builder()
                .authorUsername(comment.getAuthor().getUsername())
                .content(comment.getContent())
                .postDate(comment.getPostDate())
                .build();
    }
}
