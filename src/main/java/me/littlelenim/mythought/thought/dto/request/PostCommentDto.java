package me.littlelenim.mythought.thought.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PostCommentDto {
    @NotBlank
    @Size(max = 255)
    private final String content;
}
