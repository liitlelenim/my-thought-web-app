package me.littlelenim.mythought.thought.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostThoughtDto {
    @NotBlank
    @Size(max = 255)
    private final String content;
    @Size(min = 1,max = 5)
    private final List<@NotBlank @Size(max = 20) String> tags;
}
