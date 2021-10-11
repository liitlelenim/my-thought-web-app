package me.littlelenim.mythought.thought.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FullThoughtDto {
    private final String content;
    private final Date postDate;
    private final List<String> tags;
    private final int likesAmount;
    private final List<GetCommentDto> comments;
}
