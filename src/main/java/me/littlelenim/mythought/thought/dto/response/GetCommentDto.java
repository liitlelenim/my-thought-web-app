package me.littlelenim.mythought.thought.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class GetCommentDto {
    private final String authorUsername;
    private final String content;
    private final Date postDate;
}
