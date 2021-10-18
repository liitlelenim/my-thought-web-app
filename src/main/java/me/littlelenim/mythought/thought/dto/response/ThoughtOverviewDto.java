package me.littlelenim.mythought.thought.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ThoughtOverviewDto {
    private final long id;
    private final String content;
    private final Date postDate;
    private final List<String> likersUsernames;
    private final List<String> tags;
    private final String authorUsername;
}
